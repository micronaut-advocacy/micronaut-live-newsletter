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
    private final String id;

    @NonNull
    @NotBlank
    @Email
    private final String email;

    @NonNull
    @NotNull
    private final SubscriptionStatus status;

    @Nullable
    private final String name;

    public SubscriberRow(@NonNull String id,
                         @NonNull String email,
                         @NonNull SubscriptionStatus status) {
        this(id, email, status, null);
    }

    public SubscriberRow(@NonNull String id,
                         @NonNull String email,
                         @NonNull SubscriptionStatus status,
                         @Nullable String name) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public SubscriptionStatus getStatus() {
        return status;
    }

    @NonNull
    public String statusAlertClass() {
        return SubscriptionStatusUtils.alertClass(status);
    }

    @Nullable
    public String getName() {
        return name;
    }
}
