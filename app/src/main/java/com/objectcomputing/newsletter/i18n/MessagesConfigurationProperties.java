package com.objectcomputing.newsletter.i18n;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.NotBlank;

@ConfigurationProperties(MessagesConfigurationProperties.PREFIX)
public class MessagesConfigurationProperties implements MessagesConfiguration {
    public static final String PREFIX  = "i18n";
    public static final String DEFAULT_BASE_NAME  = "i18n.messages";
    @NonNull
    @NotBlank
    private String baseName = DEFAULT_BASE_NAME;

    @Override
    @NonNull
    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(@NonNull String baseName) {
        this.baseName = baseName;
    }
}
