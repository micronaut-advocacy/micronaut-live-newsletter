package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.naming.Named;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Introspected
public class NamedRow implements Row, Named {

    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    private final String name;

    public NamedRow(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Override
    @NonNull
    public String getName() {
        return name;
    }
}
