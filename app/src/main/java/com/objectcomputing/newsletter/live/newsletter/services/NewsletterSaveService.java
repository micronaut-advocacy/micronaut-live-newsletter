package com.objectcomputing.newsletter.live.newsletter.services;

import com.objectcomputing.newsletter.SaveService;
import com.objectcomputing.newsletter.live.services.IdGenerator;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface NewsletterSaveService extends SaveService<NewsletterSaveForm, String> {
    @Override
    @NonNull
    default Optional<String> save(@NonNull @NotNull @Valid NewsletterSaveForm form) {
        return getIdGenerator().generate().flatMap(id -> save(id, form.getName()));
    }

    Optional<String> save(@NonNull @NotBlank String id,
                          @NonNull @NotBlank String name);


    IdGenerator getIdGenerator();
}
