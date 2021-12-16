package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface UnsubscribeService {

    void unsubscribe(@NonNull @NotBlank @Email String email);
}
