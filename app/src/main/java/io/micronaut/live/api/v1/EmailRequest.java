package io.micronaut.live.api.v1;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.live.constraints.AnyContent;

import javax.validation.constraints.NotBlank;

@AnyContent
@Introspected
public class EmailRequest {
    @NonNull
    @NotBlank
    private final String subject;

    @Nullable
    private final String html;

    @Nullable
    private final String text;

    public EmailRequest(@NonNull String subject) {
        this(subject, null, null);
    }

    public EmailRequest(@NonNull String subject,
                        @Nullable String html,
                        @Nullable String text) {
        this.subject = subject;
        this.html = html;
        this.text = text;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    @Nullable
    public String getHtml() {
        return html;
    }

    @Nullable
    public String getText() {
        return text;
    }
}
