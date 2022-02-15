package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Introspected
public class SubscriberEditForm {
    @NonNull
    @NotBlank
    private String id;

    @NonNull
    @NotBlank
    @Email
    private String email;

    @Nullable
    private String emailInvalidFeedback;

    @Nullable
    private String emailValidFeedback;

    @Nullable
    private String name;

    @Nullable
    private String nameValidFeedback;

    @Nullable
    private String nameInvalidFeedback;

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getEmailInvalidFeedback() {
        return emailInvalidFeedback;
    }

    public void setEmailInvalidFeedback(@Nullable String emailInvalidFeedback) {
        this.emailInvalidFeedback = emailInvalidFeedback;
    }

    @Nullable
    public String getEmailValidFeedback() {
        return emailValidFeedback;
    }

    public void setEmailValidFeedback(@Nullable String emailValidFeedback) {
        this.emailValidFeedback = emailValidFeedback;
    }

    @Nullable
    public String getNameValidFeedback() {
        return nameValidFeedback;
    }

    public void setNameValidFeedback(@Nullable String nameValidFeedback) {
        this.nameValidFeedback = nameValidFeedback;
    }

    @Nullable
    public String getNameInvalidFeedback() {
        return nameInvalidFeedback;
    }

    public void setNameInvalidFeedback(@Nullable String nameInvalidFeedback) {
        this.nameInvalidFeedback = nameInvalidFeedback;
    }
}
