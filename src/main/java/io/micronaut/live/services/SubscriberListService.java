package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.views.SubscriberRow;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SubscriberListService {

    /**
     *
     * @param page 0 indexed page
     * @return Subscriber row
     */
    List<SubscriberRow> findAll(@NonNull @NotNull Integer page);
}
