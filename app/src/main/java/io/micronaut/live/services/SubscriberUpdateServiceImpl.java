package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.data.SubscriberDataRepository;
import io.micronaut.live.views.SubscriberDetail;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Singleton
public class SubscriberUpdateServiceImpl implements SubscriberUpdateService {
    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberUpdateServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    public void update(@NonNull @NotNull @Valid SubscriberDetail subscriberDetail) {
        subscriberDataRepository.updateNameAndEmailById(subscriberDetail.getEmail(), subscriberDetail.getName(), subscriberDetail.getId());
    }
}
