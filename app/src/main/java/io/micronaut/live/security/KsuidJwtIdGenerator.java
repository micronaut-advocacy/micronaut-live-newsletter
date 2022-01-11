package io.micronaut.live.security;

import io.micronaut.live.services.IdGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator;
import jakarta.inject.Singleton;

@Singleton
public class KsuidJwtIdGenerator implements JwtIdGenerator {
    private final IdGenerator idGenerator;

    public KsuidJwtIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public String generateJtiClaim() {
        return idGenerator.generate().orElse(null);
    }
}
