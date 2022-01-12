package io.micronaut.live.controllers;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Introspected
public class RevokeUser {

    @NonNull
    @NotBlank
    private String username;

    public RevokeUser(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getUsername() {
        return username;
    }
}
