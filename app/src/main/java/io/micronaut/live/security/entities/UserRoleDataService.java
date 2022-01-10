package io.micronaut.live.security.entities;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRoleDataService extends CrudRepository<UserRoleEntity, UserRoleId> {
}
