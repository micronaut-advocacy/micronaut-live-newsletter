package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.Subscriber;

@Introspected
public class SubscriberDetailPage extends HtmlPage {
    @NonNull
    private final SubscriberDetail subscriber;

    public SubscriberDetailPage(@NonNull String title,
                                @NonNull SubscriberDetail subscriber) {
        super(title);
        this.subscriber = subscriber;
    }

    @NonNull
    public SubscriberDetail getSubscriber() {
        return subscriber;
    }
}
