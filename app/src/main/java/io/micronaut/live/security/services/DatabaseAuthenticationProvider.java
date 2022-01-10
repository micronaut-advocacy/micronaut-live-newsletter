package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.live.security.model.UserState;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static io.micronaut.security.authentication.AuthenticationFailureReason.*;

@Singleton
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
    private final UserFetcher userFetcher;
    private final PasswordEncoderService passwordEncoder;
    private final AuthoritiesFetcher authoritiesFetcher;
    private final Scheduler scheduler;

    DatabaseAuthenticationProvider(UserFetcher userFetcher,
                                   PasswordEncoderService passwordEncoder,
                                     AuthoritiesFetcher authoritiesFetcher,
                                     @Named(TaskExecutors.IO) ExecutorService executorService) {
        this.userFetcher = userFetcher;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesFetcher = authoritiesFetcher;
        this.scheduler = Schedulers.fromExecutorService(executorService);
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.<AuthenticationResponse>create(emitter -> {
                    Optional<UserState> userOptional = fetchUserState(authenticationRequest);
            UserState user = userOptional.orElse(null);
            Optional<AuthenticationFailed> authenticationFailed = validate(authenticationRequest, user);
            if (authenticationFailed.isPresent()) {
                emitter.error(new AuthenticationException(authenticationFailed.get()));
            } else if (user == null) {
                emitter.error(new AuthenticationException(new AuthenticationFailed(UNKNOWN)));
            } else {
                emitter.success(createSuccessfulAuthenticationResponse(user));
            }
        }).subscribeOn(scheduler);
    }

    @NonNull
    private Optional<AuthenticationFailed> validate(@NonNull AuthenticationRequest<?, ?> authenticationRequest,
                                                    @Nullable UserState user) {

        AuthenticationFailed authenticationFailed = null;
        if (user == null) {
            authenticationFailed = new AuthenticationFailed(USER_NOT_FOUND);

        } else if (!user.isEnabled()) {
            authenticationFailed = new AuthenticationFailed(USER_DISABLED);

        } else if (user.isAccountExpired()) {
            authenticationFailed = new AuthenticationFailed(ACCOUNT_EXPIRED);

        } else if (user.isAccountLocked()) {
            authenticationFailed = new AuthenticationFailed(ACCOUNT_LOCKED);

        } else if (user.isPasswordExpired()) {
            authenticationFailed = new AuthenticationFailed(PASSWORD_EXPIRED);

        } else if (!passwordEncoder.matches(authenticationRequest.getSecret().toString(), user.getPassword())) {
            authenticationFailed = new AuthenticationFailed(CREDENTIALS_DO_NOT_MATCH);
        }

        return Optional.ofNullable(authenticationFailed);
    }

    @NonNull
    private Optional<UserState> fetchUserState(@NonNull AuthenticationRequest<?, ?> authRequest) {
        final String username = authRequest.getIdentity().toString();
        return userFetcher.findByUsername(username);
    }

    @NonNull
    private AuthenticationResponse createSuccessfulAuthenticationResponse(@NonNull UserState user) {
        Collection<String> authorities = authoritiesFetcher.findAuthoritiesByUsername(user.getUsername());
        return AuthenticationResponse.success(user.getUsername(), authorities);
    }
}
