package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.live.api.v1.EmailRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface EmailRequestService {
    void process(@NonNull @NotNull HttpRequest<?> request,
                 @NonNull @NotNull @Valid EmailRequest emailRequest);
}
