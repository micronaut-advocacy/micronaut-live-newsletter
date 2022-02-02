package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Serdeable.Deserializable
public class SubscriptionForm {

    @NonNull
    @NotBlank
    @Email
    private final String email;

    public SubscriptionForm(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
}
