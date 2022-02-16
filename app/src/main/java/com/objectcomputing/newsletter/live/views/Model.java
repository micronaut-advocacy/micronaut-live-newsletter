package com.objectcomputing.newsletter.live.views;

import com.objectcomputing.newsletter.live.views.validation.EmptyErrors;
import com.objectcomputing.newsletter.live.views.validation.Errors;
import com.objectcomputing.newsletter.live.views.validation.FieldError;
import com.objectcomputing.newsletter.live.views.validation.LocalizedErrors;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.Authentication;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Introspected
public class Model {

    @Nullable
    private LocalizedErrors errors = new EmptyErrors();

    @Nullable
    private Authentication authentication;

    @Nullable
    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(@Nullable Authentication authentication) {
        this.authentication = authentication;
    }

    @Nullable
    public LocalizedErrors getErrors() {
        return errors;
    }

    public void setErrors(@Nullable LocalizedErrors errors) {
        this.errors = errors;
    }


}
