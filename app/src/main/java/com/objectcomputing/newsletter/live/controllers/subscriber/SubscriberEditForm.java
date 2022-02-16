package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Serdeable.Deserializable
public class SubscriberEditForm {
    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    @Email
    private final String email;

    @Nullable
    private final String name;

    public SubscriberEditForm(@NonNull String id,
                              @NonNull String email,
                              @Nullable String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getName() {
        return name;
    }

}
