package com.objectcomputing.newsletter.live.controllers.newsletter;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import javax.validation.constraints.NotBlank;

@Serdeable.Deserializable
public class NewsletterUpdateForm {

    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    private final String name;

    public NewsletterUpdateForm(@NonNull String id,
                                @NonNull String name) {
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
