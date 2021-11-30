package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;
import io.micronaut.live.model.SubscriptionStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface SubscriberSaveService {
    @NonNull
    Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber);

    void saveActiveSubscribers(@NonNull @NotNull Collection<Subscriber> subscribers);
}
