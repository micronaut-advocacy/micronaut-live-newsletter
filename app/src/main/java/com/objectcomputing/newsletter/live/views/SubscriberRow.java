package com.objectcomputing.newsletter.live.views;

import com.objectcomputing.newsletter.live.controllers.newsletter.NewsletterShowController;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;
import io.micronaut.http.uri.UriBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Introspected
public class SubscriberRow implements Row {

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

    @Override
    @NonNull
    public Optional<String> link() {
        return Optional.of(UriBuilder.of("/subscriber").path("/show").path(getId()).build().toString());
    }
}
