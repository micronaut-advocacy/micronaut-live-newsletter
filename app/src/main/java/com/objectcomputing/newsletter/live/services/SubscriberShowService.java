package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.Subscriber;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface SubscriberShowService {

    @NonNull
    Optional<SubscriberDetail> findById(@NonNull @NotBlank String id);
}
