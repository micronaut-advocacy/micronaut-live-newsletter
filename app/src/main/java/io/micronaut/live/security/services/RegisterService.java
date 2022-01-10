package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface RegisterService {
    void register(@NonNull @NotBlank @Email String email,
                  @NonNull@NotBlank String rawPassword,
                  @NonNull List<String> authorities);
}