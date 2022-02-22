package com.objectcomputing.newsletter.live.newsletter.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotBlank;

@Serdeable.Deserializable
public class NewsletterSaveForm {

    @NonNull
    @NotBlank
    private final String name;

    public NewsletterSaveForm(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
