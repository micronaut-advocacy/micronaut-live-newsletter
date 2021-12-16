package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.StringUtils;
import io.micronaut.live.Subscriber;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class DefaultImportService implements SubscriberParserService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultImportService.class);
    public static final String AT_SIGN = "@";
    public static final String COMMA = ",";

    private final Validator validator;

    public DefaultImportService(Validator validator) {
        this.validator = validator;
    }

    @Override
    @NonNull
    public Optional<List<Subscriber>> parseSubscribers(@NonNull @NotNull InputStream inputStream) {
        try {
            String fileContents = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String[] lines = fileContents.split("\r\n");
            List<Subscriber> subscriberList = new ArrayList<>();
            for (String line : lines) {
                String[] arr = line.split(COMMA);
                for (String el : arr) {
                    if (el.contains(AT_SIGN)) {
                        String email = StringUtils.trimLeadingWhitespace(el);
                        Subscriber subscriber = new Subscriber(email);
                        if (validator.validate(subscriber).isEmpty()) {
                            subscriberList.add(subscriber);
                        }
                    }
                }
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("Parsed # subscribers: {}", subscriberList.size());
            }
            return Optional.of(subscriberList);

        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("IOException reading bytes from input stream", e);
            }
        }
        return Optional.empty();
    }
}
