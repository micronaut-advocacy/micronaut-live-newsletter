package io.micronaut.live.conf;

import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface ApiKeysConfiguration {

    @NonNull
    Optional<List<String>> getApiKeys();
}
