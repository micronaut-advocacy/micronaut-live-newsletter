package io.micronaut.live.events;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.model.Email;
import io.micronaut.live.services.ConfirmationEmailComposer;
import io.micronaut.live.services.EmailSender;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

@Singleton
public class EmailSubscriptionPendingEventListener implements ApplicationEventListener<SubscriptionPendingEvent> {

    private final ConfirmationEmailComposer confirmationEmailComposer;
    private final EmailSender emailSender;

    public EmailSubscriptionPendingEventListener(ConfirmationEmailComposer confirmationEmailComposer,
                                                 EmailSender emailSender) {
        this.confirmationEmailComposer = confirmationEmailComposer;
        this.emailSender = emailSender;
    }

    @Override
    public void onApplicationEvent(SubscriptionPendingEvent event) {
        sendEmail(event.getEmail());
    }

    @Async
    public void sendEmail(@NonNull String recipient) {
        String text = confirmationEmailComposer.composeText(recipient);
        Email email = new Email(recipient, null, text);
        emailSender.sendEmail(email);
    }
}
