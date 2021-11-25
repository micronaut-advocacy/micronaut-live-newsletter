package io.micronaut.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class AlertPage {
    @NonNull
    @NotBlank
    private final String title;

    @NonNull
    @NotNull
    @Valid
    private final Alert alert;

    public AlertPage(@NonNull String title, @NonNull Alert alert) {
        this.title = title;
        this.alert = alert;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public Alert getAlert() {
        return alert;
    }
}
