package io.micronaut.live.services.sendgrid;

import io.micronaut.core.annotation.NonNull;

public interface SendGridConfiguration {

    @NonNull
    String getApiKey();
}
