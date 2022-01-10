package io.micronaut.live.security;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.live.conf.ApiKeysConfiguration;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.validator.TokenValidator;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.Collections;

@Singleton
public class ApiKeyTokenValidator implements TokenValidator  {
    private final ApiKeysConfiguration apiKeysConfiguration;

    public ApiKeyTokenValidator(ApiKeysConfiguration apiKeysConfiguration) {
        this.apiKeysConfiguration = apiKeysConfiguration;
    }

    @Override
    public Publisher<Authentication> validateToken(String token, HttpRequest<?> request) {
        if (StringUtils.isEmpty(token)) {
            return Publishers.empty();
        }
        if (apiKeysConfiguration.getApiKeys().isEmpty()) {
            return Publishers.empty();
        }
        if (!apiKeysConfiguration.getApiKeys().get().contains(token)) {
            return Publishers.empty();
        }
        return Publishers.just(Authentication.build("sergio", Collections.singletonMap("email", "delamos@objectcomputing.com")));
    }
}
