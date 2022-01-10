package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

public interface UserRoleService {

    void grant(@NonNull @NotBlank String authority, @NonNull String userId);
}
