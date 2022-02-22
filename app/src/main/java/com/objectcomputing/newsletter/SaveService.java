package com.objectcomputing.newsletter;

import io.micronaut.core.annotation.NonNull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@FunctionalInterface
public interface SaveService<FORM, ID> {
    @NonNull
    Optional<ID> save(@NonNull @NotNull @Valid FORM form);
}
