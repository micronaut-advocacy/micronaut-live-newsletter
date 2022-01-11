package io.micronaut.live.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public class SherlockHolmesAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.<AuthenticationResponse>create(emitter -> {
            if (authenticationRequest.getIdentity().equals("sherlock@bakerstreet.com") && authenticationRequest.getSecret().equals("elementary")) {
                emitter.success(AuthenticationResponse.success("sherlock"));
            } else if (authenticationRequest.getIdentity().equals("sherlock") && authenticationRequest.getSecret().equals("elementary")) {
                emitter.success(AuthenticationResponse.success("sherlock"));
            } else {
                emitter.error(AuthenticationResponse.exception());
            }
        });
    }
}
