package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.uri.UriBuilder;

import java.net.URI;

public final class SubscriberUrlMappings {
    private SubscriberUrlMappings() {

    }

    public static URI show(@NonNull String id) {
        return UriBuilder.of("/subscriber")
                .path("show")
                .path(id)
                .build();
    }
}
