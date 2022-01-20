package io.micronaut.live.security.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.GenericRepository;
import io.micronaut.live.security.model.UserState;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserDataService extends GenericRepository<UserEntity, String> {
    @NonNull
    UserEntity save(@NonNull UserEntity entity);

    @NonNull
    Optional<UserEntity> findById(@NonNull @NotBlank String id);

    @NonNull
    Optional<UserEntity> findByEmail(@NonNull @NotBlank String email);

    long countByEmail(@NonNull @NotBlank String email);
}
