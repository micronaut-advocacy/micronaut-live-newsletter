package io.micronaut.live.conf;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import java.util.List;
import java.util.Optional;

public interface AllowedLdapUsersConfiguration {
    @NonNull
    Optional<List<String>> getUsers();
}
