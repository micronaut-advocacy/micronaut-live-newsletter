package com.objectcomputing.newsletter.live.services;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.Subscriber;
import com.objectcomputing.newsletter.live.data.SubscriberDataRepository;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Singleton
public class SubscriberShowServiceImpl implements SubscriberShowService {

    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberShowServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @NonNull
    public Optional<SubscriberDetail> findById(@NonNull @NotBlank String id) {
        return subscriberDataRepository.find(id);
    }
}
