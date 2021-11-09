package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.live.model.SubscriptionStatus;

@JdbcRepository(dialect = Dialect.H2)
public interface SubscriberDataRepository extends CrudRepository<SubscriberEntity, String> {

    Integer countByStatus(SubscriptionStatus subscriptionStatus);
}
