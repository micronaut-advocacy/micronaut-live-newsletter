package io.micronaut.live.conf;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import java.util.List;
import java.util.Optional;

@ConfigurationProperties("ldapusers")
public class AllowedLdapUsersConfigurationProperties implements AllowedLdapUsersConfiguration {

    @Nullable
    private List<String> users;

    public void setUsers(@Nullable List<String> users) {
        this.users = users;
    }

    @Override
    @NonNull
    public Optional<List<String>> getUsers() {
        return Optional.ofNullable(users);
    }
}
