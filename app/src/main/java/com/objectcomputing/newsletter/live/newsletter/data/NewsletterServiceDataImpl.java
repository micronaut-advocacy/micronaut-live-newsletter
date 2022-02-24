package com.objectcomputing.newsletter.live.newsletter.data;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterDeleteService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterDetail;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterEditService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterListService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterPaginatedList;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterSaveService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterShowService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterUpdateForm;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterUpdateService;
import com.objectcomputing.newsletter.live.services.IdGenerator;
import com.objectcomputing.newsletter.live.views.Pagination;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Function;

@Singleton
public class NewsletterServiceDataImpl implements NewsletterSaveService,
        NewsletterListService,
        NewsletterShowService,
        NewsletterEditService,
        NewsletterUpdateService,
        NewsletterDeleteService {
    private final IdGenerator idGenerator;
    private final NewsletterDataRepository newsletterDataRepository;
    private final Integer itemsPerPage;
    private static final Function<Integer, String> PAGE_URI_BUILDER = pageInt -> UriBuilder.of("/newsletter")
            .path("list")
            .queryParam("page", pageInt)
            .build()
            .toString();

    public NewsletterServiceDataImpl(IdGenerator idGenerator, NewsletterDataRepository newsletterDataRepository) {
        this.idGenerator = idGenerator;
        this.newsletterDataRepository = newsletterDataRepository;
        this.itemsPerPage = 5;
    }

    @Override
    @NonNull
    public Optional<String> save(@NonNull @NotBlank String id,
                                 @NonNull @NotBlank String name) {
        newsletterDataRepository.save(id, name);
        return Optional.of(id);
    }

    @Override
    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    @Override
    @NonNull
    public NewsletterPaginatedList findAll(@NonNull @NotNull @Min(1) Integer page) {
        int total = newsletterDataRepository.count();
        Pagination pagination = Pagination.of(total, itemsPerPage, PAGE_URI_BUILDER, page);
        Pageable pageable = Pageable.from((page - 1), itemsPerPage);
        return new NewsletterPaginatedList(newsletterDataRepository.find(pageable), pagination);
    }

    @Override
    @NonNull
    public Optional<NewsletterDetail> find(@NonNull @NotBlank String id) {
        return newsletterDataRepository.find(id);
    }

    @Override
    @NonNull
    public Optional<NewsletterUpdateForm> edit(@NonNull @NotBlank String id) {
        return newsletterDataRepository.find(id)
                .map(it -> new NewsletterUpdateForm(it.getId(), it.getName()));
    }

    @Override
    public void update(@NonNull @NotNull @Valid NewsletterUpdateForm form) {
        newsletterDataRepository.update(form.getId(), form.getName());
    }

    @Override
    public void delete(@NonNull @NotBlank String id) {
        newsletterDataRepository.deleteById(id);
    }
}
