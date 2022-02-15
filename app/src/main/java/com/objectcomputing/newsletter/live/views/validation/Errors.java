package com.objectcomputing.newsletter.live.views.validation;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Errors {

    @NonNull
    Optional<List<FieldError>> fieldErrors();

    @NonNull
    default Optional<List<FieldError>> fieldErrorsByFieldName(@NonNull @NotBlank String fieldName) {
        Optional<List<FieldError>> allFieldErrors = fieldErrors();
        if (!allFieldErrors.isPresent()) {
            return allFieldErrors;
        }
        List<FieldError> result = new ArrayList<>();
        for (FieldError fieldError : allFieldErrors.get()) {
            if (fieldError.getField().equals(fieldName)) {
                result.add(fieldError);
            }
        }
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
