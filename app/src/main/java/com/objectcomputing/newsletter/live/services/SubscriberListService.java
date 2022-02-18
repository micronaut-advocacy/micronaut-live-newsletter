package com.objectcomputing.newsletter.live.services;

import com.objectcomputing.newsletter.live.views.SubscriberListModel;
import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface SubscriberListService {

    /**
     *
     * @param page 1 indexed page
     * @return Subscriber row
     */
    @NonNull
    SubscriberListModel findAll(@NonNull @NotNull  @Min(1) Integer page);
}
