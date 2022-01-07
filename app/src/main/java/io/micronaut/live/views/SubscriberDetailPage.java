package io.micronaut.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;

@Introspected
public class SubscriberDetailPage extends HtmlPage {
    @NonNull
    private final Subscriber subscriber;

    public SubscriberDetailPage(@NonNull String title,
                                @NonNull Subscriber subscriber) {
        super(title);
        this.subscriber = subscriber;
    }

    @NonNull
    public Subscriber getSubscriber() {
        return subscriber;
    }
}
