package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.views.SubscriberListPage;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface SubscriberListService {

    /**
     *
     * @param page 1 indexed page
     * @return Subscriber row
     */
    @NonNull
    SubscriberListPage findAll(@NonNull @NotNull  @Min(1) Integer page);
}
