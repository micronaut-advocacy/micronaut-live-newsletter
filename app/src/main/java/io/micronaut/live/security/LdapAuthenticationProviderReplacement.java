package io.micronaut.live.security;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.ldap.ContextAuthenticationMapper;
import io.micronaut.security.ldap.LdapAuthenticationProvider;
import io.micronaut.security.ldap.configuration.LdapConfiguration;
import io.micronaut.security.ldap.context.ContextBuilder;
import io.micronaut.security.ldap.context.LdapSearchResult;
import io.micronaut.security.ldap.context.LdapSearchService;
import io.micronaut.security.ldap.group.LdapGroupProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Named("forumsys")
@Replaces(LdapAuthenticationProvider.class)
@Singleton
public class LdapAuthenticationProviderReplacement extends LdapAuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(LdapAuthenticationProvider.class);

    private final LdapConfiguration configuration;
    private final LdapSearchService ldapSearchService;
    private final ContextBuilder contextBuilder;
    private final ContextAuthenticationMapper contextAuthenticationMapper;
    private final LdapGroupProcessor ldapGroupProcessor;
    private final Scheduler scheduler;

    public LdapAuthenticationProviderReplacement(@Named("forumsys") LdapConfiguration configuration,
                                                 LdapSearchService ldapSearchService, ContextBuilder contextBuilder,
                                                 ContextAuthenticationMapper contextAuthenticationMapper,
                                                 LdapGroupProcessor ldapGroupProcessor,
                                                 @Named(TaskExecutors.IO) ExecutorService executorService) {
        super(configuration, ldapSearchService, contextBuilder, contextAuthenticationMapper, ldapGroupProcessor, executorService);
        this.configuration = configuration;
        this.ldapSearchService = ldapSearchService;
        this.contextBuilder = contextBuilder;
        this.contextAuthenticationMapper = contextAuthenticationMapper;
        this.ldapGroupProcessor = ldapGroupProcessor;
        this.scheduler = Schedulers.fromExecutorService(executorService);
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        Flux<AuthenticationResponse> reactiveSequence = Flux.create(emitter -> {
            String username = authenticationRequest.getIdentity().toString();
            String password = authenticationRequest.getSecret().toString();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Starting authentication with configuration [{}]", configuration.getName());
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Attempting to initialize manager context");
            }
            DirContext managerContext;
            try {
                managerContext = contextBuilder.build(configuration.getManagerSettings());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Manager context initialized successfully");
                }
            } catch (NamingException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Failed to create manager context. Returning unknown authentication failure. Encountered {}", e.getMessage());
                }
                emitter.error(AuthenticationResponse.exception(AuthenticationFailureReason.UNKNOWN));
                return;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Attempting to authenticate with user [{}]", username);
            }

            try {
                Optional<LdapSearchResult> optionalResult = ldapSearchService.searchFirst(managerContext, configuration.getSearch().getSettings(new Object[]{username}));

                if (optionalResult.isPresent()) {
                    LdapSearchResult result = optionalResult.get();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("User found in context [{}]. Attempting to bind.", result.getDn());
                    }

                    DirContext userContext = null;
                    try {
                        String dn = result.getDn();
                        userContext = contextBuilder.build(configuration.getSettings(result.getDn(), password));
                        if (result.getAttributes() == null) {
                            result.setAttributes(userContext.getAttributes(dn));
                        }
                    } finally {
                        contextBuilder.close(userContext);
                    }

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Successfully bound user [{}]. Attempting to retrieving groups.", result.getDn());
                    }

                    Set<String> groups = Collections.emptySet();

                    LdapConfiguration.GroupConfiguration groupSettings = configuration.getGroups();
                    if (groupSettings.isEnabled()) {
                        groups = ldapGroupProcessor.process(groupSettings.getAttribute(), result, () -> {
                            Object[] params = new Object[]{
                                    groupSettings.getFilterAttribute()
                                            .map(attr -> result.getAttributes().getValue(attr))
                                            .orElse(result.getDn())
                            };
                            return ldapSearchService.search(managerContext, groupSettings.getSearchSettings(params));
                        });


                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Group search returned [{}] for user [{}]", groups, username);
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Group search is disabled for configuration [{}]", configuration.getName());
                        }
                    }

                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Attempting to map [{}] with groups [{}] to an authentication response.", username, groups);
                    }

                    groups.add("ROLE_LDAP");
                    AuthenticationResponse response = contextAuthenticationMapper.map(result.getAttributes(), username, groups);
                    if (response.isAuthenticated()) {
                        emitter.next(response);
                        emitter.complete();
                    } else {
                        emitter.error(new AuthenticationException(response));
                    }

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Response successfully created for [{}]. Response is authenticated: [{}]", username, response.isAuthenticated());
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("User not found [{}]", username);
                    }
                    emitter.error(AuthenticationResponse.exception(AuthenticationFailureReason.USER_NOT_FOUND));
                }
            } catch (NamingException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Failed to authenticate with user [{}].  {}", username, e);
                }
                if (e instanceof javax.naming.AuthenticationException) {
                    emitter.error(AuthenticationResponse.exception(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
                } else {
                    emitter.error(e);
                }
            } finally {
                contextBuilder.close(managerContext);
            }
        }, FluxSink.OverflowStrategy.ERROR);
        reactiveSequence = reactiveSequence.subscribeOn(scheduler);
        return reactiveSequence;
    }

    @Override
    public void close() { }
}
