package io.micronaut.live.events;

import io.micronaut.context.annotation.Secondary;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.model.Email;
import io.micronaut.live.services.EmailSender;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Secondary
@Singleton
public class SecondaryEmailSender implements EmailSender {
    @Override
    public void sendEmail(@NonNull @NotNull @Valid Email email) {

    }
}
