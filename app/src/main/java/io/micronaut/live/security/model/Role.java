package io.micronaut.live.security.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Introspected
public class Role {

    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    private final String authority;

    public Role(@NonNull String id, @NonNull String authority) {
        this.id = id;
        this.authority = authority;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getAuthority() {
        return authority;
    }
}
