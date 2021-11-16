package io.micronaut.live.services;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.model.Email;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Requires(env = Environment.DEVELOPMENT)
@Singleton
public class DevEmailSender implements EmailSender {
    private static final Logger LOG = LoggerFactory.getLogger(DevEmailSender.class);
    @Override
    public void sendEmail(@NonNull @NotNull @Valid Email email) {
        if (LOG.isInfoEnabled()) {
            LOG.info("{}", email);
        }
    }
}
