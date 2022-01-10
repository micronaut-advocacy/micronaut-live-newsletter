package io.micronaut.live.security;

import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.token.reader.TokenReader;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class ApiKeyTokenReader implements TokenReader {

    public static final String API_KEY = "apiKey";

    @Override
    public Optional<String> findToken(HttpRequest<?> request) {
        return Optional.ofNullable(request.getParameters().get(API_KEY));
    }
}
