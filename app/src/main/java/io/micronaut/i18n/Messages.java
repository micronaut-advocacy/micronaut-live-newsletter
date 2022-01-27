package io.micronaut.i18n;

import io.micronaut.context.MessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.LocaleResolver;
import io.micronaut.http.HttpRequest;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class Messages {
    private final MessageSource messageSource;
    private final Locale locale;

    public Messages(LocaleResolver<HttpRequest<?>> localeResolver,
                    MessageSource messageSource,
                    HttpRequest<?> request) {
        this.locale = localeResolver.resolveOrDefault(request);
        this.messageSource = messageSource;
    }

    @NonNull
    public String get(@NonNull String code, @NonNull String defaultMessage) {
        return messageSource.getMessage(code, getContext(), defaultMessage);
    }

    @NonNull
    public Optional<String> get(@NonNull String code) {
        return messageSource.getMessage(code, getContext());
    }

    @NonNull
    public String get(@NonNull String code, @NonNull List<Object> args, String defaultMessage) {
        return get(code, args).orElse(defaultMessage);
    }

    @NonNull
    public Optional<String> get(@NonNull String code, @NonNull List<Object> args) {
        Map<String, Object> variables = MessageSourceUtils.variables(args.toArray());
        return messageSource.getMessage(code, MessageSource.MessageContext.of(variables));
    }

    @NonNull
    private MessageSource.MessageContext getContext() {
        return (locale != null) ? MessageSource.MessageContext.of(locale) : MessageSource.MessageContext.DEFAULT;
    }
}
