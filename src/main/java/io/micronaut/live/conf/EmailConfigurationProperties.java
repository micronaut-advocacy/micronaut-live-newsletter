package io.micronaut.live.conf;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Requires(property = EmailConfigurationProperties.PREFIX + ".from")
@ConfigurationProperties(EmailConfigurationProperties.PREFIX)
public class EmailConfigurationProperties implements EmailConfiguration {
    public static final String PREFIX = "email";

    @NonNull
    @NotBlank
    private String from;

    @Override
    @NonNull
    public String getFrom() {
        return from;
    }

    public void setFrom(@NonNull String from) {
        this.from = from;
    }
}
