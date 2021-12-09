package io.micronaut.live.conf;

import io.micronaut.core.annotation.NonNull;

public interface SubscriberConfiguration {

    @NonNull
    Integer getSubscriberListPageSize();
}
