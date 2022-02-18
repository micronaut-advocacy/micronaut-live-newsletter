package com.objectcomputing.newsletter.live.data;

import com.objectcomputing.newsletter.live.views.SubscriberListModel;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.uri.UriBuilder;
import com.objectcomputing.newsletter.live.conf.SubscriberConfiguration;
import com.objectcomputing.newsletter.live.services.SubscriberListService;
import com.objectcomputing.newsletter.live.views.Pagination;
import jakarta.inject.Singleton;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

@Singleton
public class SubscriberListServiceImpl implements SubscriberListService {

    private final SubscriberDataRepository subscriberDataRepository;
    private final SubscriberConfiguration subscriberConfiguration;
    private static final Function<Integer, String> PAGE_URI_BUILDER = pageInt -> UriBuilder.of("/subscriber")
            .path("list")
            .queryParam("page", pageInt)
            .build()
            .toString();

    public SubscriberListServiceImpl(SubscriberDataRepository subscriberDataRepository,
                                     SubscriberConfiguration subscriberConfiguration) {
        this.subscriberDataRepository = subscriberDataRepository;
        this.subscriberConfiguration = subscriberConfiguration;
    }

    @Override
    @NonNull
    public SubscriberListModel findAll(@NonNull @NotNull @Min(1) Integer page) {
        int max = subscriberConfiguration.getSubscriberListPageSize();
        Pageable pageable = Pageable.from((page - 1), max);
        Pagination pagination = Pagination.of(subscriberDataRepository.count(), max, PAGE_URI_BUILDER, page);
        return new SubscriberListModel(subscriberDataRepository.find(pageable), pagination);
    }
}
