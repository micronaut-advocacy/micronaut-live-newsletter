package io.micronaut.live.views;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.model.SubscriptionStatus;

public final class SubscriptionStatusUtils {

    @NonNull
    public static String alertClass(@NonNull SubscriptionStatus subscriptionStatus) {
        switch (subscriptionStatus) {
            case ACTIVE:
                return "alert-success";
            case CANCELED:
                return "alert-danger";
            default:
            case PENDING:
                return "alert-warning";
        }
    }
}
