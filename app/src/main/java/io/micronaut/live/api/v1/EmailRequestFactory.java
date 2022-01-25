package io.micronaut.live.api.v1;

import io.micronaut.context.annotation.Factory;
import io.micronaut.email.validation.AnyContent;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
public class EmailRequestFactory {
    /**
     * @return A {@link ConstraintValidator} implementation of a {@link AnyContent} validator for type {@link EmailRequest}
     */
    @Singleton
    public ConstraintValidator<AnyContent, EmailRequest> anyContentEmailRequestConstraintValidator() {
        return (value, annotationMetadata, context) -> {
            if (value == null) {
                return true;
            }
            return value.getHtml() != null || value.getText() != null;
        };
    }
}
