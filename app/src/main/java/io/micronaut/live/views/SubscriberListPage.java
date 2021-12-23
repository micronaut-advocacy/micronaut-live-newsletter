package io.micronaut.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

@Introspected
public class SubscriberListPage extends HtmlPage {
    @NonNull
    @NotNull
    private final Pagination pagination;

    @NonNull
    @NotNull
    private final List<SubscriberRow> rows;

    public SubscriberListPage(@NonNull String title,
                              @NonNull List<SubscriberRow> rows,
                              @NonNull Pagination pagination) {
        super(title);
        this.rows = rows;
        this.pagination = pagination;
    }

    @NonNull
    public Pagination getPagination() {
        return pagination;
    }

    @NonNull
    public List<SubscriberRow> getRows() {
        return rows;
    }
}
