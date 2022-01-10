package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.NotBlank;

public interface PasswordEncoderService {
    String encode(@NotBlank @NonNull String rawPassword);

    boolean matches(@NotBlank @NonNull String rawPassword,
                    @NotBlank @NonNull String encodedPassword);
}
