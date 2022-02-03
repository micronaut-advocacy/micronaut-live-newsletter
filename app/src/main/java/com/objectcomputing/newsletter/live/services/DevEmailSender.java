package com.objectcomputing.newsletter.live.services;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Secondary;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.email.Contact;
import io.micronaut.email.Email;
import io.micronaut.email.EmailException;
import io.micronaut.email.TransactionalEmailSender;
import io.micronaut.email.sendgrid.SendGridConfigurationProperties;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

@Requires(missingProperty = SendGridConfigurationProperties.PREFIX + ".api-key")
@Requires(env = Environment.DEVELOPMENT)
@Secondary
@Singleton
@Named(DevEmailSender.NAME)
public class DevEmailSender implements TransactionalEmailSender<Email, Void> {
    private static final Logger LOG = LoggerFactory.getLogger(DevEmailSender.class);
    public static final String NAME = "devemailsender";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    @NonNull
    public Void send(@NonNull @NotNull @Valid Email email,
                     @NonNull @NotNull Consumer<Email> emailRequest) throws EmailException {
        LOG.info("to: {} subject: {}",
                CollectionUtils.isNotEmpty(email.getTo()) ? email.getTo().stream().findFirst().map(Contact::getEmail).orElse("") : "",
                email.getSubject());
        return null;
    }
}
