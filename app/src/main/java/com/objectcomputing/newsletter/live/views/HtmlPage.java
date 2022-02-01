package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.Authentication;
import javax.validation.constraints.NotBlank;

@Introspected
public class HtmlPage {
    @NonNull
    @NotBlank
    private final String title;

    @Nullable
    private Authentication authentication;

    public HtmlPage(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @Nullable
    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(@Nullable Authentication authentication) {
        this.authentication = authentication;
    }
}
