package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.data.SubscriberDataRepository;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;
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
