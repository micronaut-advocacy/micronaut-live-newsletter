package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SubscriberSaveService {
    void save(@NonNull @NotNull @Valid Subscriber subscriber);
}
