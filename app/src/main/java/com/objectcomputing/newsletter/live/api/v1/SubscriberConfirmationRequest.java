package com.objectcomputing.newsletter.live.api.v1;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Introspected
public class SubscriberConfirmationRequest {

    @NonNull
    @NotBlank
    private final String token;

    public SubscriberConfirmationRequest(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public String getToken() {
        return token;
    }
}
