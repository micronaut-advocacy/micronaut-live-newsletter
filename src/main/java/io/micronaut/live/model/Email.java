package io.micronaut.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;

@Introspected
public class Email {

    @NonNull
    @NotBlank
    @javax.validation.constraints.Email
    private final String to;

    @Nullable
    private final String html;

    @Nullable
    private final String text;

    public Email(@NonNull String to,
                 @Nullable String html,
                 @Nullable String text) {
        this.to = to;
        this.html = html;
        this.text = text;
    }

    @NonNull
    public String getTo() {
        return to;
    }

    @Nullable
    public String getHtml() {
        return html;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", html='" + html + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
