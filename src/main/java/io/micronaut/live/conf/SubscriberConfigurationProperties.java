package io.micronaut.live.conf;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;

@ConfigurationProperties("newsletter")
public class SubscriberConfigurationProperties implements SubscriberConfiguration {
    private static final Integer DEFAULT_SUBSCRIBER_LIST_PAGE_SIZE = 20;

    @NonNull
    @NotNull
    private Integer subscriberListPageSize = DEFAULT_SUBSCRIBER_LIST_PAGE_SIZE;

    @Override
    @NonNull
    public Integer getSubscriberListPageSize() {
        return subscriberListPageSize;
    }

    public void setSubscriberListPageSize(@NonNull Integer subscriberListPageSize) {
        this.subscriberListPageSize = subscriberListPageSize;
    }
}
