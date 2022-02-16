package com.objectcomputing.newsletter.live.views.validation;

import io.micronaut.core.annotation.NonNull;

import java.util.List;

public interface LocalizedErrors {

    @NonNull
    List<String> getFieldErrors(@NonNull String fieldName);
}
