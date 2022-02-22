package com.objectcomputing.newsletter.live.newsletter.services;

import com.objectcomputing.newsletter.live.views.PaginatedList;
import com.objectcomputing.newsletter.live.views.Pagination;
import io.micronaut.core.annotation.Introspected;
import java.util.List;

@Introspected
public class NewsletterPaginatedList extends PaginatedList {
    public NewsletterPaginatedList(List<NewsletterRow> rows, Pagination pagination) {
        super(rows, pagination);
    }
}
