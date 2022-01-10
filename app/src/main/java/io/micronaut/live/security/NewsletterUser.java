package io.micronaut.live.security;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Introspected
public class NewsletterUser implements Principal {
    @NonNull
    private final String username;

    @NonNull
    private final Collection<String> roles;

    @Nullable
    private final String email;

    public NewsletterUser(@NonNull String username,
                          @NonNull Collection<String> roles,
                          @Nullable String email) {
        this.username = username;
        this.roles = roles;
        this.email = email;
    }

    @Override
    public String getName() {
        return username;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public Collection<String> getRoles() {
        return roles;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}
