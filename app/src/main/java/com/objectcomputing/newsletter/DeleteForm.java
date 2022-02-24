package com.objectcomputing.newsletter;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable.Deserializable
public class DeleteForm<ID> {
    @NonNull
    private final ID id;

    public DeleteForm(@NonNull ID id) {
        this.id = id;
    }

    @NonNull
    public ID getId() {
        return id;
    }
}
