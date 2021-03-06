package com.objectcomputing.newsletter.live.newsletter.services;

import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface NewsletterListService {
    @NonNull
    NewsletterPaginatedList findAll(@NonNull @NotNull @Min(1) Integer page);
}
