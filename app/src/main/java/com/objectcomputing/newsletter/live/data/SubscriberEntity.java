package com.objectcomputing.newsletter.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedEntity("subscriber")
public class SubscriberEntity {

    @Id
    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    private final String email;

    @Nullable
    private final String name;

    @NonNull
    @NotNull
    private final SubscriptionStatus status;

    public SubscriberEntity(@NonNull String id,
                            @NonNull String email,
                            @Nullable String name) {
        this(id, email, name, SubscriptionStatus.PENDING);
    }

    public SubscriberEntity(@NonNull String id,
                            @NonNull String email,
                            @Nullable String name,
                            SubscriptionStatus status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.status = status;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @NonNull
    public SubscriptionStatus getStatus() {
        return status;
    }
}
