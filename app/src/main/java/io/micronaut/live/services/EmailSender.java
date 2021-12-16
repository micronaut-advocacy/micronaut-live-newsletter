package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.model.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface EmailSender {

    void sendEmail(@NonNull @NotNull @Valid Email email);
}
