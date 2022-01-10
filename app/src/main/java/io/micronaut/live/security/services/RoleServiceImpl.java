package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.security.entities.RoleDataService;
import io.micronaut.live.security.entities.RoleEntity;
import io.micronaut.live.security.model.Role;
import io.micronaut.live.services.IdGenerator;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Singleton
public class RoleServiceImpl implements RoleService {
    private final IdGenerator idGenerator;
    private final RoleDataService roleDataService;

    public RoleServiceImpl(IdGenerator idGenerator,
                           RoleDataService roleDataService) {
        this.idGenerator = idGenerator;
        this.roleDataService = roleDataService;
    }

    @Override
    @NonNull
    public Optional<String> save(@NonNull @NotBlank String authority) {
        return idGenerator.generate().map(id -> {
            RoleEntity roleEntity = new RoleEntity(id, authority);
            roleDataService.save(roleEntity);
            return id;
        });
    }

    @Override
    public boolean existsByAuthority(@NonNull @NotBlank String authority) {
        return roleDataService.countByAuthority(authority) > 0;
    }
}
