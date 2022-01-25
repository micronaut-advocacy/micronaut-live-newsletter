package io.micronaut.live.constraints;

import io.micronaut.context.annotation.Factory;
import io.micronaut.core.util.StringUtils;
import io.micronaut.live.api.v1.EmailRequest;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
public class AnyContentConstraintValidatorFactory {
    /**
     * @return A {@link ConstraintValidator} implementation of a {@link AnyContent} validator for type {@link EmailRequest}
     */
    @Singleton
    public ConstraintValidator<AnyContent, EmailRequest> anyContentEmailRequestConstraintValidator() {
        return (value, annotationMetadata, context) -> {
            if (value == null) {
                return true;
            }
            return StringUtils.isNotEmpty(value.getHtml()) || StringUtils.isNotEmpty(value.getText());
        };
    }
}
