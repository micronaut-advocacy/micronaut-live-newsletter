package io.micronaut.live.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.live.conf.AllowedLdapUsersConfiguration;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

//@Singleton
public class AllowedUsersAuthenticationProvider implements AuthenticationProvider  {
    private final AllowedLdapUsersConfiguration allowedLdapUsersConfiguration;

    public AllowedUsersAuthenticationProvider(AllowedLdapUsersConfiguration allowedLdapUsersConfiguration) {
        this.allowedLdapUsersConfiguration = allowedLdapUsersConfiguration;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.<AuthenticationResponse>create(emitter -> {
            String username = authenticationRequest.getIdentity().toString();
            if (allowedLdapUsersConfiguration.getUsers().isPresent() &&
                    allowedLdapUsersConfiguration.getUsers().get().contains(username)) {
                emitter.success(AuthenticationResponse.success(username));
            } else {
                emitter.error(AuthenticationResponse.exception());
            }
        });
    }
}
