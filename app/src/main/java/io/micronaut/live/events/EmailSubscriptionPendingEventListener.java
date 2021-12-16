package io.micronaut.live.events;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.conf.EmailConfiguration;
import io.micronaut.live.model.Email;
import io.micronaut.live.services.ConfirmationEmailComposer;
import io.micronaut.live.services.EmailSender;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

@Requires(beans = EmailConfiguration.class)
@Singleton
public class EmailSubscriptionPendingEventListener implements ApplicationEventListener<SubscriptionPendingEvent> {

    private final EmailConfiguration emailConfiguration;
    private final ConfirmationEmailComposer confirmationEmailComposer;
    private final EmailSender emailSender;

    public EmailSubscriptionPendingEventListener(EmailConfiguration emailConfiguration,
                                                 ConfirmationEmailComposer confirmationEmailComposer,
                                                 EmailSender emailSender) {
        this.emailConfiguration = emailConfiguration;
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
        Email email = Email.builder()
                .to(recipient)
                .subject("Confirm your subscription") //TODO localize this
                .from(emailConfiguration.getFrom())
                .text(text)
                .build();
        emailSender.sendEmail(email);
    }
}
