package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface SubscriberParserService {

    @NonNull
    Optional<List<Subscriber>> parseSubscribers(@NonNull @NotNull InputStream inputStream);
}
