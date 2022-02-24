package com.objectcomputing.newsletter.live.newsletter.data;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterDetail;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterRow;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface NewsletterDataRepository extends GenericRepository<NewsletterEntity, String> {

    NewsletterEntity save(@NonNull @NotBlank String id, @NonNull @NotBlank String name);

    @NonNull
    Integer count();

    @NonNull
    Optional<NewsletterDetail> find(@NonNull @NotBlank String id);

    @NonNull
    List<NewsletterRow> find(@NonNull @NotNull Pageable pageable);

    void update(@Id @NonNull @NotBlank String id, @NonNull @NotBlank String name);

    void deleteById(@NonNull @NotBlank String id);
}
