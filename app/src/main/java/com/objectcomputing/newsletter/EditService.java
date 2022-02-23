package com.objectcomputing.newsletter;

import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@FunctionalInterface
public interface EditService<FORM, ID> {
    @NonNull
    Optional<FORM> edit(@NonNull @NotNull ID id);
}
