package io.micronaut.live.security;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;

@MappedEntity("refreshtoken")
public class RefreshTokenEntity {
    @Id
    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    private final String username;

    public RefreshTokenEntity(@NonNull String id, @NonNull String username) {
        this.id = id;
        this.username = username;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }
}
