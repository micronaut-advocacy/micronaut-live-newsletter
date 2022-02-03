package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.Subscriber;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface SubscriberSaveService {
    boolean exists(@NonNull @NotBlank @Email String email);

    @NonNull
    Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber);

    void saveActiveSubscribers(@NonNull @NotNull Collection<Subscriber> subscribers);

    @NonNull
    Optional<String> saveActiveSubscriber(@NonNull @NotNull @Valid Subscriber subscriber);
}
