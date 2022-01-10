package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Singleton
public class BCryptPasswordEncoderService implements PasswordEncoderService {

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @NonNull
    public String encode(@NotBlank @NonNull String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(@NotBlank @NonNull String rawPassword,
                    @NotBlank @NonNull String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
