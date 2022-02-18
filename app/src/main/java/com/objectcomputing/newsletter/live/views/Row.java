package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.NonNull;
import java.util.Optional;

public interface Row {

    @NonNull
    default Optional<String> link() {
        return Optional.empty();
    }
}
