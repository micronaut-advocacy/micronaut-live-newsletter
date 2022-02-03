package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface ConfirmationCodeVerifier {
    /**
     *
     * @param token The token associated with the user
     * @return The email address associated with the token or empty if the token could not be validated
     */
    @NonNull
    Optional<String> verify(@NonNull @NotBlank String token);
}
