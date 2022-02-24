package com.objectcomputing.newsletter;

import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@FunctionalInterface
public interface DeleteService<ID> {
    void delete(@NonNull @NotNull ID id);

    default void delete(@NonNull @NotNull @Valid DeleteForm<ID> form) {
        delete(form.getId());
    }
}
