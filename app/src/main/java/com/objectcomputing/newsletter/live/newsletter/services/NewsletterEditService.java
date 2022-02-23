package com.objectcomputing.newsletter.live.newsletter.services;

import com.objectcomputing.newsletter.EditService;
import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@FunctionalInterface
public interface NewsletterEditService extends EditService<NewsletterUpdateForm, String> {

    @Override
    @NonNull
    Optional<NewsletterUpdateForm> edit(@NonNull @NotBlank String id);
}
