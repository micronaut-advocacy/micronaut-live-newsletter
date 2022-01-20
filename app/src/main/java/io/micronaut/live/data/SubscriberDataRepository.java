package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.live.Subscriber;
import io.micronaut.live.model.SubscriptionStatus;
import io.micronaut.live.views.SubscriberDetail;
import io.micronaut.live.views.SubscriberRow;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface SubscriberDataRepository extends CrudRepository<SubscriberEntity, String> {

    @NonNull
    Integer countByStatus(@NonNull @NotNull SubscriptionStatus subscriptionStatus);

    @Query("UPDATE subscriber SET status = :status WHERE email = :email")
    void updateStatusByEmail(@NonNull @NotNull SubscriptionStatus status,
                             @NonNull @NotNull String email);

    @NonNull
    List<Subscriber> findByStatus(@NonNull @NotNull SubscriptionStatus subscriptionStatus);

    long countByEmail(@NonNull @NotNull @Email String email);

    @NonNull
    List<SubscriberRow> find(Pageable pageable);

    @NonNull
    Optional<SubscriberDetail> find(String id);
}
