package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface SubscriberShowService {

    @NonNull
    Optional<Subscriber> findById(@NonNull @NotBlank String id);
}
