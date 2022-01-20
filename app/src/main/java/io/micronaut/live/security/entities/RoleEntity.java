package io.micronaut.live.security.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;

@MappedEntity("role")
public class RoleEntity {

    @Id
    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    private final String authority;

    public RoleEntity(@NonNull String id, @NonNull String authority) {
        this.id = id;
        this.authority = authority;
    }

    @NonNull
    public String getAuthority() {
        return authority;
    }

    @NonNull
    public String getId() {
        return id;
    }
}
