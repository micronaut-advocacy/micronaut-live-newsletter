package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

@Introspected
public class PaginatedList extends Model {
    @NonNull
    @NotNull
    private final Pagination pagination;

    @NonNull
    @NotNull
    private final List<? extends Row> rows;

    public PaginatedList(@NonNull List<? extends Row> rows,
                         @NonNull Pagination pagination) {
        this.rows = rows;
        this.pagination = pagination;
    }

    @NonNull
    public Pagination getPagination() {
        return pagination;
    }

    @NonNull
    public List<? extends Row> getRows() {
        return rows;
    }
}
