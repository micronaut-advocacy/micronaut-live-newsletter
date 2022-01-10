package io.micronaut.live.conf;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@ConfigurationProperties("appsecurity")
public class ApiKeyConfigurationProperties implements ApiKeysConfiguration {

    @Nullable
    private List<String> apiKeys;

    @Override
    @NonNull
    public Optional<List<String>> getApiKeys() {
        return Optional.ofNullable(apiKeys);
    }

    public void setApiKeys(@Nullable List<String> apiKeys) {
        this.apiKeys = apiKeys;
    }
}
