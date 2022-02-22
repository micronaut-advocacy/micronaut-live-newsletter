package com.objectcomputing.newsletter;

import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@FunctionalInterface
public interface ShowService<ID, DETAIL>  {
    @NonNull
    Optional<DETAIL> find(@NonNull @NotNull ID id);
}
