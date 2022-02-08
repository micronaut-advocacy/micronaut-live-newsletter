package io.micronaut.views.turbo;

import io.micronaut.core.annotation.NonNull;

import java.util.Optional;

public class StringTemplate implements Template {
    @NonNull
    private final String template;

    public StringTemplate(@NonNull String template) {
        this.template = template;
    }

    @Override
    public Optional<String> get() {
        return Optional.of(template);
    }
}
