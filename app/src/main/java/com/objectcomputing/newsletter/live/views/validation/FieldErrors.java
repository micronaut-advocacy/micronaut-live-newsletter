package com.objectcomputing.newsletter.live.views.validation;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;

@Introspected
public class FieldErrors implements Errors {

    private final List<FieldError> fieldErrors;

    public FieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    @Override
    @NonNull
    public Optional<List<FieldError>> fieldErrors() {
        return fieldErrors.isEmpty() ? Optional.empty() : Optional.of(fieldErrors);
    }
}
