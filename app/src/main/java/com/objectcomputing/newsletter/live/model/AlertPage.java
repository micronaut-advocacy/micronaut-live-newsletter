package com.objectcomputing.newsletter.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.views.Model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Introspected
public class AlertPage extends Model {
    @NonNull
    @NotNull
    @Valid
    private final Alert alert;

    public AlertPage(@NonNull Alert alert) {
        this.alert = alert;
    }

    @NonNull
    public Alert getAlert() {
        return alert;
    }
}
