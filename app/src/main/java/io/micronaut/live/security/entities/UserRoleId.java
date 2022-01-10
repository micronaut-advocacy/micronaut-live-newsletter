package io.micronaut.live.security.entities;

import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.Relation;

import java.util.Objects;

@Embeddable
public class UserRoleId {

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private final UserEntity user;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private final RoleEntity role;

    public UserRoleId(UserEntity user, RoleEntity role) {
        this.user = user;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRoleId userRoleId = (UserRoleId) o;
        return role.getId().equals(userRoleId.getRole().getId()) &&
                user.getId().equals(userRoleId.getUser().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(role.getId(), user.getId());
    }

    public UserEntity getUser() {
        return user;
    }

    public RoleEntity getRole() {
        return role;
    }
}
