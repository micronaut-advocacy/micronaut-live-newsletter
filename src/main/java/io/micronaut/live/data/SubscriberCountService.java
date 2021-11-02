package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;

public interface SubscriberCountService {

    @NonNull
    Integer countConfirmedSubscribers();
}
