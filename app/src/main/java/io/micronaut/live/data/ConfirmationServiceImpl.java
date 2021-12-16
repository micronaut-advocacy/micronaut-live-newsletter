package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.model.SubscriptionStatus;
import io.micronaut.live.services.ConfirmationService;
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
