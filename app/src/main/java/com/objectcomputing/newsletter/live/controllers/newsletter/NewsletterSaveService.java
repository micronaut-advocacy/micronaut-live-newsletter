package com.objectcomputing.newsletter.live.controllers.newsletter;

import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface NewsletterSaveService {
    @NonNull
    Optional<String> save(@NonNull @NotNull @Valid NewsletterSaveForm form);
}
