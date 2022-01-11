package io.micronaut.live.security;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.config.TokenConfiguration;
import io.micronaut.security.token.jwt.generator.claims.ClaimsAudienceProvider;
import io.micronaut.security.token.jwt.generator.claims.ClaimsGenerator;
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator;
import jakarta.inject.Singleton;

@Replaces(ClaimsGenerator.class)
@Singleton
public class CustomClaimsGenerator extends JWTClaimsSetGenerator {
    public CustomClaimsGenerator(TokenConfiguration tokenConfiguration,
                                 @Nullable JwtIdGenerator jwtIdGenerator,
                                 @Nullable ClaimsAudienceProvider claimsAudienceProvider,
                                 @Nullable ApplicationConfiguration applicationConfiguration) {
        super(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider, applicationConfiguration);
    }

    @Override
    protected void populateWithAuthentication(JWTClaimsSet.Builder builder, Authentication authentication) {
        super.populateWithAuthentication(builder, authentication);
        builder.claim("custom", "mycustomvalue");
    }
}
