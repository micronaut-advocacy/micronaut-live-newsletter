package com.objectcomputing.newsletter.live.controllers.newsletter;

import com.objectcomputing.newsletter.live.services.IdGenerator;
import com.objectcomputing.newsletter.live.views.Pagination;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Singleton
public class NewsletterListServiceImpl implements NewsletterListService {
    private final List<NewsletterRow> rows;
    private static final Function<Integer, String> PAGE_URI_BUILDER = page -> UriBuilder.of(NewsletterListController.PATH_NEWSLETTER_LIST)
            .queryParam("page", page)
            .build()
            .toString();

    public NewsletterListServiceImpl(IdGenerator idGenerator) {
        rows = Arrays.asList(
                new NewsletterRow(idGenerator.generate().get(), "A"),
                new NewsletterRow(idGenerator.generate().get(), "B"),
                new NewsletterRow(idGenerator.generate().get(), "C"),
                new NewsletterRow(idGenerator.generate().get(), "D"),
                new NewsletterRow(idGenerator.generate().get(), "E"),
                new NewsletterRow(idGenerator.generate().get(), "F"),
                new NewsletterRow(idGenerator.generate().get(), "G"),
                new NewsletterRow(idGenerator.generate().get(), "H"),
                new NewsletterRow(idGenerator.generate().get(), "I"),
                new NewsletterRow(idGenerator.generate().get(), "J"),
                new NewsletterRow(idGenerator.generate().get(), "K"),
                new NewsletterRow(idGenerator.generate().get(), "M"),
                new NewsletterRow(idGenerator.generate().get(), "N"),
                new NewsletterRow(idGenerator.generate().get(), "L"),
                new NewsletterRow(idGenerator.generate().get(), "O"),
                new NewsletterRow(idGenerator.generate().get(), "P"),
                new NewsletterRow(idGenerator.generate().get(), "Q"),
                new NewsletterRow(idGenerator.generate().get(), "R"),
                new NewsletterRow(idGenerator.generate().get(), "S"),
                new NewsletterRow(idGenerator.generate().get(), "T"),
                new NewsletterRow(idGenerator.generate().get(), "U"),
                new NewsletterRow(idGenerator.generate().get(), "V"),
                new NewsletterRow(idGenerator.generate().get(), "W"),
                new NewsletterRow(idGenerator.generate().get(), "Z")
        );
    }

    @Override
    @NonNull
    public NewsletterPaginatedList findAll(@NonNull @NotNull @Min(1) Integer page) {
        int total = rows.size();
        int perPage = 5;
        int from = Math.min(total, perPage * page) - perPage;
        int to = from + perPage;
        Pagination pagination = Pagination.of(total, perPage, PAGE_URI_BUILDER, page);
        return new NewsletterPaginatedList(rows.subList(from, to), pagination);
    }
}
