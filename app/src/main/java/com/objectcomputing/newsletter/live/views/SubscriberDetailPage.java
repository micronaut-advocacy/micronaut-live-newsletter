package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class SubscriberDetailPage extends Model {
    @NonNull
    private final SubscriberDetail subscriber;

    public SubscriberDetailPage(@NonNull SubscriberDetail subscriber) {
        this.subscriber = subscriber;
    }

    @NonNull
    public SubscriberDetail getSubscriber() {
        return subscriber;
    }
}
