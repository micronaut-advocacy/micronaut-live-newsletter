package com.objectcomputing.newsletter.i18n;

import io.micronaut.context.MessageSource;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.LocaleResolver;
import io.micronaut.http.HttpRequest;
import jakarta.inject.Singleton;

@Singleton
public class MessagesBuilder {
    private final MessageSource messageSource;
    private final LocaleResolver<HttpRequest<?>> localeResolver;

    public MessagesBuilder(LocaleResolver<HttpRequest<?>> localeResolver,
                           MessagesConfiguration messagesConfiguration) {
        this.localeResolver = localeResolver;
        messageSource = new ResourceBundleMessageSource(messagesConfiguration.getBaseName());
    }

    @NonNull
    public Messages build(@NonNull HttpRequest<?> request) {
        return new Messages(localeResolver, messageSource, request);
    }
}
