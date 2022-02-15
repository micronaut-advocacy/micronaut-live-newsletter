package com.objectcomputing.newsletter.live.views.validation;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;

@Introspected
public class FieldError {

    @NonNull
    @NotBlank
    private String field;

    @NonNull
    @NotBlank
    private String code;

    @NonNull
    private String defaultMessage;

    @Nullable
    private Object rejectedValue;

    public FieldError(@NonNull String field,
                      @NonNull String code,
                      @NonNull String defaultMessage) {
        this(field, code, defaultMessage, null);
    }

    public FieldError(@NonNull String field,
                      @NonNull String code,
                      @NonNull String defaultMessage,
                      @Nullable Object rejectedValue) {
        this.field = field;
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.rejectedValue = rejectedValue;
    }

    @NonNull
    public String getField() {
        return field;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    @NonNull
    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Nullable
    public Object getRejectedValue() {
        return rejectedValue;
    }
}

