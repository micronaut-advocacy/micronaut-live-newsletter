package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface AuthoritiesFetcher {
    @NonNull
    Collection<String> findAuthoritiesByUsername(@NonNull @NotBlank String username);
}
