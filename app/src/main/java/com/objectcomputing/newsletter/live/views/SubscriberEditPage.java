package com.objectcomputing.newsletter.live.views;

import com.objectcomputing.newsletter.live.controllers.subscriber.SubscriberEditForm;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class SubscriberEditPage extends HtmlPage {
    @NonNull
    private final SubscriberEditForm subscriber;

    public SubscriberEditPage(@NonNull String title,
                              @NonNull SubscriberEditForm subscriber) {
        super(title);
        this.subscriber = subscriber;
    }

    @NonNull
    public SubscriberEditForm getSubscriber() {
        return subscriber;
    }
}
