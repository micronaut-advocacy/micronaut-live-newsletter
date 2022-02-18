package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import java.util.List;

@Introspected
public class SubscriberListModel extends PaginatedList {
    public SubscriberListModel(@NonNull List<SubscriberRow> rows,
                              @NonNull Pagination pagination) {
        super(rows, pagination);
    }
}
