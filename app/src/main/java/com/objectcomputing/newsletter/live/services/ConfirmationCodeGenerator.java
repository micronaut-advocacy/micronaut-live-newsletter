package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;

import java.util.Optional;

public interface ConfirmationCodeGenerator {

    @NonNull
    Optional<String> generate(@NonNull String email);
}
