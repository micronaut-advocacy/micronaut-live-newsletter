package com.objectcomputing.newsletter.live.newsletter.services;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Introspected
public class NewsletterDetail {
    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    private final String name;

    public NewsletterDetail(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
