package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SubscriberUpdateService {
    void update(@NonNull @NotNull @Valid SubscriberDetail subscriberDetail);
}
