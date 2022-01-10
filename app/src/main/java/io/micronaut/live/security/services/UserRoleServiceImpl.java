package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.security.entities.RoleDataService;
import io.micronaut.live.security.entities.RoleEntity;
import io.micronaut.live.security.entities.UserDataService;
import io.micronaut.live.security.entities.UserEntity;
import io.micronaut.live.security.entities.UserRoleDataService;
import io.micronaut.live.security.entities.UserRoleEntity;
import io.micronaut.live.security.entities.UserRoleId;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Singleton
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleDataService userRoleDataService;
    private final RoleDataService roleDataService;
    private final UserDataService userDataService;

    public UserRoleServiceImpl(UserRoleDataService userRoleDataService,
                               RoleDataService roleDataService,
                               UserDataService userDataService) {
        this.userRoleDataService = userRoleDataService;
        this.roleDataService = roleDataService;
        this.userDataService = userDataService;
    }

    @Override
    public void grant(@NonNull @NotBlank String authority, @NonNull String userId) {

        Optional<RoleEntity> roleOptional = roleDataService.findByAuthority(authority);
        if (roleOptional.isPresent()) {
            RoleEntity role = roleOptional.get();
            Optional<UserEntity> userOptional = userDataService.findById(userId);
            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();
                userRoleDataService.save(new UserRoleEntity(new UserRoleId(userEntity, role)));
            }
        }
    }
}
