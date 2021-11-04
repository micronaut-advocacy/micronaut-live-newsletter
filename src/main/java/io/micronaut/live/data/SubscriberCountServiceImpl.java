package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

@Singleton
public class SubscriberCountServiceImpl implements SubscriberCountService {

    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberCountServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    @NonNull
    public Integer countSubscribers() {
        return subscriberDataRepository.countByConfirmedAndUnsubscribed(true, false);
    }
}
