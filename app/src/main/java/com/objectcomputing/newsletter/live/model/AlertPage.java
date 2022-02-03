package com.objectcomputing.newsletter.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import com.objectcomputing.newsletter.live.views.HtmlPage;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class AlertPage extends HtmlPage  {
    @NonNull
    @NotNull
    @Valid
    private final Alert alert;

    public AlertPage(@NonNull String title, @NonNull Alert alert) {
        super(title);
        this.alert = alert;
    }

    @NonNull
    public Alert getAlert() {
        return alert;
    }
}
