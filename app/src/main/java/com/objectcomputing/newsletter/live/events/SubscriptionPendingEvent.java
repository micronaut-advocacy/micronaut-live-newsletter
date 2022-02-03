package com.objectcomputing.newsletter.live.events;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class SubscriptionPendingEvent {

    @NonNull
    private final String email;

    public SubscriptionPendingEvent(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
}
