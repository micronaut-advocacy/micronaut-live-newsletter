package com.objectcomputing.newsletter.live.views.validation;

import io.micronaut.core.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class EmptyErrors implements LocalizedErrors {

    @Override
    @NonNull
    public List<String> getFieldErrors(@NonNull String fieldName) {
        return Collections.emptyList();
    }
}
