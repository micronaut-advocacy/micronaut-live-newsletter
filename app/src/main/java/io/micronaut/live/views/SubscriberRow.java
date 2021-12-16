package io.micronaut.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.live.model.SubscriptionStatus;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class SubscriberRow {

    @NonNull
    @NotBlank
    @Email
    private final String email;

    @NonNull
    @NotNull
    private final SubscriptionStatus status;

    @Nullable
    private final String name;

    public SubscriberRow(@NonNull String email,
                         @NonNull SubscriptionStatus status,
                         @Nullable String name) {
        this.email = email;
        this.status = status;
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public SubscriptionStatus getStatus() {
        return status;
    }

    @Nullable
    public String getName() {
        return name;
    }
}
