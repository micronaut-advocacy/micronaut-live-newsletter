package io.micronaut.live.security.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Introspected
public class User {
    @Id
    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    @Email
    private final String email;

    private final boolean enabled;

    private final boolean accountExpired;

    private final boolean accountLocked;

    private final boolean passwordExpired;

    public User(@NonNull String id,
                @NonNull String email,
                        boolean enabled,
                        boolean accountExpired,
                        boolean accountLocked,
                        boolean passwordExpired) {
        this.id = id;
        this.email = email;
        this.enabled = enabled;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.passwordExpired = passwordExpired;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }
}
