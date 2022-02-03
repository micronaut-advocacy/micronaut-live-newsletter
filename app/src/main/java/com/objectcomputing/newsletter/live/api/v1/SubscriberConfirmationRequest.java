package com.objectcomputing.newsletter.live.api.v1;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import javax.validation.constraints.NotBlank;

@Serdeable.Deserializable
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
