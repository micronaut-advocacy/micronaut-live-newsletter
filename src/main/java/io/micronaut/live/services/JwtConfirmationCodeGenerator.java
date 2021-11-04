package io.micronaut.live.services;

import com.nimbusds.jwt.JWT;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.signature.SignatureConfiguration;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JwtValidator;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class JwtConfirmationCodeGenerator implements ConfirmationCodeGenerator, ConfirmationCodeVerifier {
    private static final Logger LOG = LoggerFactory.getLogger(JwtConfirmationCodeGenerator.class);
    public static final String CLAIM_EMAIL = "email";
    private final TokenGenerator tokenGenerator;
    private final JwtValidator jwtValidator;

    public JwtConfirmationCodeGenerator(TokenGenerator tokenGenerator,
                                        Collection<SignatureConfiguration> signatureConfigurations,
                                        Collection<EncryptionConfiguration> encryptionConfigurations,
                                        ExpirationJwtClaimsValidator expirationJwtClaimsValidator) {
        this.tokenGenerator = tokenGenerator;
        this.jwtValidator = JwtValidator.builder()
                .withSignatures(signatureConfigurations)
                .withEncryptions(encryptionConfigurations)
                .withClaimValidators(Collections.singletonList(expirationJwtClaimsValidator))
                .build();
    }

    @Override
    @NonNull
    public Optional<String> verify(@NonNull @NotBlank String token) {
        Optional<JWT> optionalJWT = jwtValidator.validate(token, null);
        if (!optionalJWT.isPresent()) {
            return Optional.empty();
        }
        JWT jwt = optionalJWT.get();
        try {
            Object claim = jwt.getJWTClaimsSet().getClaim(CLAIM_EMAIL);
            if (claim == null) {
                return Optional.empty();
            }
            return Optional.of(claim.toString());
        } catch (ParseException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Could not get the Claims", e);
            }
        }
        return Optional.empty();
    }

    @Override
    @NonNull
    public Optional<String> generate(@NonNull String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.EXPIRATION_TIME, Date.from(Instant.now().plus(3600, ChronoUnit.SECONDS))); //TODO move 3600 to configuration
        claims.put(CLAIM_EMAIL, email);
        return tokenGenerator.generateToken(claims);
    }
}
