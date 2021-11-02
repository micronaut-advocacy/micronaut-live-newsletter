package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

public interface ConfirmationCodeVerifier {
    boolean verify(@NonNull @NotBlank String token);
}
