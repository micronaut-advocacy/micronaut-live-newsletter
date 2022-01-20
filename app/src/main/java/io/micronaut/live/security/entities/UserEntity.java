package io.micronaut.live.security.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@MappedEntity("user")
public class UserEntity {
    @Id
    @NonNull
    @NotBlank
    private final String id;

    @NonNull
    @NotBlank
    @Email
    private final String email;

    @NonNull
    @NotBlank
    private final String password;

    private final boolean enabled;

    @MappedProperty("account_expired")
    private final boolean accountExpired;

    @MappedProperty("account_locked")
    private final boolean accountLocked;

    @MappedProperty("password_expired")
    private final boolean passwordExpired;

    public UserEntity(@NonNull String id,
                      @NonNull String email,
                      @NonNull String password) {
        this(id, email, password, true, false, false, false);
    }

    public UserEntity(@NonNull String id,
                      @NonNull String email,
                      @NonNull String password,
                      boolean enabled,
                      boolean accountExpired,
                      boolean accountLocked,
                      boolean passwordExpired) {
        this.id = id;
        this.email = email;
        this.password = password;
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

    @NonNull
    public String getPassword() {
        return password;
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
