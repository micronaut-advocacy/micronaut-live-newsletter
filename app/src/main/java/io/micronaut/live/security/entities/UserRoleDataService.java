package io.micronaut.live.security.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRoleDataService extends CrudRepository<UserRoleEntity, UserRoleId> {

    @Query("select authority from role inner join user_role ur on role.id = ur.id_role_id inner join \"user\" u on u.id = ur.id_user_id where email = :e")
    @NonNull
    List<String> findByEmail(@NonNull @NotBlank String e);

}
