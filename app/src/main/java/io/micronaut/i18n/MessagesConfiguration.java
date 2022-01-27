package io.micronaut.i18n;

import io.micronaut.core.annotation.NonNull;

public interface MessagesConfiguration {
    @NonNull
    String getBaseName();
}
