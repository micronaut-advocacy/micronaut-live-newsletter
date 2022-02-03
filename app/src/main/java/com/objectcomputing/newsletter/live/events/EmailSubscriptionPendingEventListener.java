package com.objectcomputing.newsletter.live.events;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.email.Email;
import io.micronaut.email.EmailSender;
import com.objectcomputing.newsletter.live.services.ConfirmationEmailComposer;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

@Requires(beans = EmailSender.class)
@Singleton
public class EmailSubscriptionPendingEventListener implements ApplicationEventListener<SubscriptionPendingEvent> {
    private final ConfirmationEmailComposer confirmationEmailComposer;
    private final EmailSender<?, ?> emailSender;

    public EmailSubscriptionPendingEventListener(ConfirmationEmailComposer confirmationEmailComposer,
                                                 EmailSender<?, ?> emailSender) {
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
        Email.Builder email = Email.builder()
                .to(recipient)
                .subject("Confirm your subscription") //TODO localize this
                .body(text);
        emailSender.send(email);
    }
}
