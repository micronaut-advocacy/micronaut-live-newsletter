package com.objectcomputing.newsletter.live.data;

import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.Subscriber;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    @NonNull
    public Integer countSubscribers() {
        return subscriberDataRepository.countByStatus(SubscriptionStatus.ACTIVE);
    }

    @Override
    @NonNull
    public List<Subscriber> findAll() {
        return subscriberDataRepository.findByStatus(SubscriptionStatus.ACTIVE);
    }
}
