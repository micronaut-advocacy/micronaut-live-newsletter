package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;

@Singleton
public class UnsubscribeServiceImpl implements UnsubscribeService {
    @Override
    public void unsubscribe(@NonNull @NotBlank String email) {

    }
}
