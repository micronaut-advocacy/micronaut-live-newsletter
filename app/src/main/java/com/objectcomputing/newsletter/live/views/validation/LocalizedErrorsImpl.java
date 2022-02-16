package com.objectcomputing.newsletter.live.views.validation;

import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LocalizedErrorsImpl implements LocalizedErrors {

    private final LocalizedMessageSource localizedMessageSource;
    private final Errors errors;

    public LocalizedErrorsImpl(LocalizedMessageSource localizedMessageSource,
                               Errors errors) {
        this.localizedMessageSource = localizedMessageSource;
        this.errors = errors;
    }

    @Override
    @NonNull
    public List<String> getFieldErrors(@NonNull String fieldName) {
        if (localizedMessageSource == null || errors == null) {
            return Collections.emptyList();
        }
        Optional<List<FieldError>> fieldErrorListOptional = errors.fieldErrorsByFieldName(fieldName);
        List<String> errorMessages = new ArrayList<>();
        if (fieldErrorListOptional.isPresent()) {
            List<FieldError> fieldErrorList = fieldErrorListOptional.get();
            for (FieldError fieldError : fieldErrorList) {
                errorMessages.add(localizedMessageSource.getMessageOrDefault(fieldError.getCode(), fieldError.getDefaultMessage()));
            }
        }
        return errorMessages;
    }
}
