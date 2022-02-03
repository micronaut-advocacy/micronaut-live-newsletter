package com.objectcomputing.newsletter.live;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Serdeable.Deserializable
public class Subscriber {

    @NonNull
    @NotBlank
    @Email
    private final String email;

    @Nullable
    private final String name;

    public Subscriber(@NonNull String email) {
        this(email, null);
    }
    public Subscriber(@NonNull String email,
                      @Nullable String name) {
        this.email = email;
        this.name = name;
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
