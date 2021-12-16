package io.micronaut.live.services.sendgrid;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Requires(property = SendGridConfigurationProperties.PREFIX + ".api-key")
@ConfigurationProperties(SendGridConfigurationProperties.PREFIX)
public class SendGridConfigurationProperties implements SendGridConfiguration {
    public final static String PREFIX = "sendgrid";

    @NonNull
    @NotBlank
    private String apiKey;

    @Override
    @NonNull
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(@NonNull String apiKey) {
        this.apiKey = apiKey;
    }
}
