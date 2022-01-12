package io.micronaut.live.security;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.services.IdGenerator;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.generator.RefreshTokenGenerator;
import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class KsuidRefreshTokenGenerator implements RefreshTokenGenerator {

    private final IdGenerator idGenerator;

    public KsuidRefreshTokenGenerator(RefreshTokenDataRepository refreshTokenDataRepository, IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    @NonNull
    public String createKey(@NonNull Authentication authentication) {
        return idGenerator.generate().orElseGet(() -> UUID.randomUUID().toString());
    }

    @Override
    @NonNull
    public Optional<String> generate(@NonNull Authentication authentication, @NonNull String token) {
        return Optional.of(token);
    }
}
