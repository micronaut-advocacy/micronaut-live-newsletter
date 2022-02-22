package com.objectcomputing.newsletter.live.newsletter.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import javax.validation.constraints.NotBlank;

@MappedEntity("newsletter")
public class NewsletterEntity {
    @Id
    @NonNull
    @NotBlank
    private final String id;

    @Nullable
    private final String name;

    public NewsletterEntity(@NonNull String id,
                            @Nullable String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }
}
