package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.security.model.UserState;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserFetcher {
    @NonNull
    Optional<UserState> findByUsername(@NotBlank @NonNull String username);
}
