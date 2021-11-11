package io.micronaut.live.controllers;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Introspected
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
