package io.micronaut.launch;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Introspected
public class VersionsResponse {

    @NonNull
    @NotBlank
    private final Map<String, String> versions;

    public VersionsResponse(@NonNull Map<String, String> versions) {
        this.versions = versions;
    }

    @NonNull
    public Map<String, String> getVersions() {
        return versions;
    }
}
