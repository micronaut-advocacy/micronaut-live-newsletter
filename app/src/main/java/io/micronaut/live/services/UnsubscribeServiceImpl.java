package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.data.SubscriberDataRepository;
import io.micronaut.live.model.SubscriptionStatus;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;

@Singleton
public class UnsubscribeServiceImpl implements UnsubscribeService {
    private final SubscriberDataRepository subscriberDataRepository;

    public UnsubscribeServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    public void unsubscribe(@NonNull @NotBlank String email) {
        subscriberDataRepository.updateStatusByEmail(SubscriptionStatus.CANCELED, email);
    }
}
