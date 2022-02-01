package com.objectcomputing.newsletter.live.data;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;
import com.objectcomputing.newsletter.live.services.ConfirmationService;
import jakarta.inject.Singleton;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Singleton
public class ConfirmationServiceImpl implements ConfirmationService {
    private final SubscriberDataRepository subscriberDataRepository;

    public ConfirmationServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    public void confirm(@NonNull @NotNull @Email String email) {
        subscriberDataRepository.updateStatusByEmail(SubscriptionStatus.ACTIVE, email);
    }
}
