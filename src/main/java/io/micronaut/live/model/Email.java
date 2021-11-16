package io.micronaut.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Introspected
public class Email {

    @NonNull
    @NotBlank
    @javax.validation.constraints.Email
    private final String from;

    @NonNull
    @NotBlank
    @javax.validation.constraints.Email
    private final String to;

    @NonNull
    @NotBlank
    private final String subject;

    @Nullable
    private final List<@javax.validation.constraints.Email String> cc;

    @Nullable
    private final List<@javax.validation.constraints.Email String> bcc;

    @Nullable
    private final String html;

    @Nullable
    private final String text;

    public Email(@NonNull String from,
                 @NonNull String to,
                 @NonNull String subject,
                 @Nullable List<String> cc,
                 @Nullable List<String> bcc,
                 @Nullable String html,
                 @Nullable String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.cc = cc;
        this.bcc = bcc;
        this.html = html;
        this.text = text;
    }

    @NonNull
    public String getFrom() {
        return from;
    }

    @Nullable
    public List<String> getCc() {
        return cc;
    }

    @Nullable
    public List<String> getBcc() {
        return bcc;
    }

    @NonNull
    public String getSubject() {
        return subject;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String from;
        private String to;
        private String subject;
        private List<String> cc;
        private List<String> bcc;
        private String html;
        private String text;

        @NonNull
        public Builder from(@NonNull String from) {
            this.from = from;
            return this;
        }

        @NonNull
        public Builder to(@NonNull String to) {
            this.to = to;
            return this;
        }

        @NonNull
        public Builder subject(@NonNull String subject) {
            this.subject = subject;
            return this;
        }

        @NonNull
        public Builder cc(@NonNull String cc) {
            if (this.cc == null) {
                this.cc = new ArrayList<>();
            }
            this.cc.add(cc);
            return this;
        }

        @NonNull
        public Builder bcc(@NonNull String bcc) {
            if (this.bcc == null) {
                this.bcc = new ArrayList<>();
            }
            this.bcc.add(bcc);
            return this;
        }

        @NonNull
        public Builder html(@NonNull String html) {
            this.html = html;
            return this;
        }

        @NonNull
        public Builder text(@NonNull String text) {
            this.text = text;
            return this;
        }

        @NonNull
        public Email build() {
            return new Email(Objects.requireNonNull(from),
                    Objects.requireNonNull(to),
                    Objects.requireNonNull(subject),
                    cc,
                    bcc,
                    html,
                    text);
        }
    }
}
