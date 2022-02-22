package com.objectcomputing.newsletter.live.newsletter.controller;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Inject;

@Introspected
public class NewsletterCreateForm {
    @Nullable
    private final String name;

    public NewsletterCreateForm() {
        this(null);
    }

    @Inject
    public NewsletterCreateForm(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }
}
