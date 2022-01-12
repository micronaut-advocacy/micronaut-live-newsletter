package io.micronaut.live.security;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.validator.RefreshTokenValidator;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class KsuiRefreshTokenValidator implements RefreshTokenValidator {
    private final RefreshTokenDataRepository refreshTokenDataRepository;

    public KsuiRefreshTokenValidator(RefreshTokenDataRepository refreshTokenDataRepository) {
        this.refreshTokenDataRepository = refreshTokenDataRepository;
    }

    @Override
    @NonNull
    public Optional<String> validate(@NonNull String refreshToken) {
        return refreshTokenDataRepository.findById(refreshToken)
                .map(RefreshTokenEntity::getId);
    }
}
