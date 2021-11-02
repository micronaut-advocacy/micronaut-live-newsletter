package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import javax.validation.constraints.NotBlank;

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

    private final boolean confirmed;

    public SubscriberEntity(@NonNull String id,
                            @NonNull String email,
                            @Nullable String name) {
        this(id, email, name, false);
    }

    public SubscriberEntity(@NonNull String id,
                            @NonNull String email,
                            @Nullable String name,
                            boolean confirmed) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.confirmed = confirmed;
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

    public boolean isConfirmed() {
        return confirmed;
    }
}
