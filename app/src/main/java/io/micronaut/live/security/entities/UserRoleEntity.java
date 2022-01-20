package io.micronaut.live.security.entities;

import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;

@MappedEntity("user_role")
public class UserRoleEntity {

    @EmbeddedId
    private final UserRoleId id;

    public UserRoleEntity(UserRoleId id) {
        this.id = id;
    }

    public UserRoleId getId() {
        return id;
    }

    @Transient
    public UserEntity getUser() {
        return id.getUser();
    }

    @Transient
    public RoleEntity getRole() {
        return id.getRole();
    }
}

