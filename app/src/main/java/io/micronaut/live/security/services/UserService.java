package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.security.model.UserState;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserService {

    @NonNull
    Optional<String> save(@NonNull @NotBlank @Email String email,
                          @NonNull @NotBlank String rawPassword);

    @NonNull
    Boolean existsByEmail(@NonNull @NotBlank @Email String email);
}
