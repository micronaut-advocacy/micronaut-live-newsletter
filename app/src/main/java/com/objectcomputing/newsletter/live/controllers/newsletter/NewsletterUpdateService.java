package com.objectcomputing.newsletter.live.controllers.newsletter;

import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface NewsletterUpdateService {
    void update(@NonNull @NotNull @Valid NewsletterUpdateForm form);
}
