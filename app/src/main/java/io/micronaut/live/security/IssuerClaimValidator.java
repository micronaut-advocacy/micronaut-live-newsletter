package io.micronaut.live.security;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator;
import jakarta.inject.Singleton;

@Singleton
public class IssuerClaimValidator implements GenericJwtClaimsValidator {
    @Override
    public boolean validate(@NonNull JwtClaims claims, @Nullable HttpRequest<?> request) {
        if (claims.contains(JwtClaims.ISSUER) &&
                claims.get(JwtClaims.ISSUER).toString().equals("newsletter")) {
                return true;
        }
        return false;
    }
}
