package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;
import io.micronaut.live.views.SubscriberDetail;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface SubscriberShowService {

    @NonNull
    Optional<SubscriberDetail> findById(@NonNull @NotBlank String id);
}
