package io.micronaut.live.services;

import com.nimbusds.jwt.JWT;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.signature.SignatureConfiguration;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JwtAuthenticationFactory;
import io.micronaut.security.token.jwt.validator.JwtValidator;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Singleton
public class ConfirmationCodeVerifierImpl implements ConfirmationCodeVerifier {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmationCodeVerifierImpl.class);
    private final JwtValidator jwtValidator;

    public ConfirmationCodeVerifierImpl(Collection<SignatureConfiguration> signatureConfigurations,
                                        Collection<EncryptionConfiguration> encryptionConfigurations,
                                        ExpirationJwtClaimsValidator expirationJwtClaimsValidator) {
        this.jwtValidator = JwtValidator.builder()
                .withSignatures(signatureConfigurations)
                .withEncryptions(encryptionConfigurations)
                .withClaimValidators(Collections.singletonList(expirationJwtClaimsValidator))
                .build();
    }

    @Override
    public boolean verify(@NonNull @NotBlank String token) {
        Optional<JWT> optionalJWT = jwtValidator.validate(token, null);
        return optionalJWT.isPresent();
    }

}
