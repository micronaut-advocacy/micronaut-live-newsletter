package com.objectcomputing.newsletter.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import com.objectcomputing.newsletter.live.Subscriber;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;
import com.objectcomputing.newsletter.live.views.SubscriberRow;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface SubscriberDataRepository extends CrudRepository<SubscriberEntity, String> {

    @NonNull
    Integer countByStatus(@NonNull @NotNull SubscriptionStatus subscriptionStatus);

    @Query("UPDATE subscriber SET email = :email, name = :name WHERE id = :id")
    void updateNameAndEmailById(@NonNull @NotBlank @Email String email,
                                @Nullable String name,
                                @NonNull @NotBlank String id);

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
