package io.micronaut.live.security;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface RefreshTokenDataRepository extends CrudRepository<RefreshTokenEntity, String> {
    @NonNull
    List<RefreshTokenEntity> findByUsername(@NonNull @NotBlank String username);
}
