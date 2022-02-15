package com.objectcomputing.newsletter.live.views.validation;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class ViolationUtils {

    @NonNull
    public static Errors createErrors(@NonNull Set<ConstraintViolation<?>> violations) {
        List<FieldError> fieldErrors = new ArrayList<>();
        for (ConstraintViolation<?> violation : violations) {
            Optional<String> fieldOptional = getField(violation);
            String code = getCode(violation);
            if (fieldOptional.isPresent()) {
                String field = fieldOptional.get();
                FieldError fieldError = new FieldError(field, code, violation.getMessage(), violation.getInvalidValue());
                fieldErrors.add(fieldError);
            }
        }
        return new FieldErrors(fieldErrors);
    }
    @NonNull
    public static Errors createErrors(@NonNull ConstraintViolationException ex) {
        return createErrors(ex.getConstraintViolations());
    }

    @NonNull
    private static Optional<String> getField(@NonNull final ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        int index = path.lastIndexOf('.');
        if (index == -1) {
            return Optional.empty();
        }
        return Optional.of(path.substring(path.lastIndexOf('.') + 1));
    }

    @NonNull
    private static String getCode(@NonNull final ConstraintViolation<?> violation) {
        String[] arr = violation.getPropertyPath().toString().split("\\.");
        List<String> result = new ArrayList<>();
        if (arr.length >= 2) {
            for (int i = arr.length - 2; i < arr.length; i++) {
                result.add(arr[i]);
            }
        }
        String messageTemplate = violation.getMessageTemplate();
        if (messageTemplate.startsWith("{") && messageTemplate.endsWith(".message}")) {
            String tmpl = messageTemplate.substring(1, messageTemplate.length() - ".message}".length());
            int index = tmpl.lastIndexOf('.');
            if (index != -1) {
                result.add(decapitalize(tmpl.substring(index + 1)));
            }

        }
        return String.join(".", result);
    }

    @Nullable
    private static String decapitalize(@Nullable String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);

        return new String(c);
    }
}
