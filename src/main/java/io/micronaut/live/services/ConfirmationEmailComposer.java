package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface ConfirmationEmailComposer {

    @NonNull
    String composeText(@NonNull @NotBlank String email);
}
