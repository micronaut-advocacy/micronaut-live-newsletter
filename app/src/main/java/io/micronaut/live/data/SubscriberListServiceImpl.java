package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.i18n.Messages;
import io.micronaut.live.conf.SubscriberConfiguration;
import io.micronaut.live.services.SubscriberListService;
import io.micronaut.live.views.Page;
import io.micronaut.live.views.Pagination;
import io.micronaut.live.views.SubscriberListPage;
import io.micronaut.live.views.SubscriberRow;
import jakarta.inject.Singleton;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class SubscriberListServiceImpl implements SubscriberListService {

    private final SubscriberDataRepository subscriberDataRepository;
    private final SubscriberConfiguration subscriberConfiguration;
    private final String pagePath;

    public SubscriberListServiceImpl(SubscriberDataRepository subscriberDataRepository,
                                     SubscriberConfiguration subscriberConfiguration) {
        this.subscriberDataRepository = subscriberDataRepository;
        this.subscriberConfiguration = subscriberConfiguration;
        pagePath = UriBuilder.of("/subscriber")
                .path("list")
                .build()
                .toString();
    }

    @Override
    @NonNull
    public SubscriberListPage findAll(@NonNull @NotNull @Min(1) Integer page,
                                      @NonNull Messages messages) {
        int max = subscriberConfiguration.getSubscriberListPageSize();
        Pageable pageable = Pageable.from((page - 1), max);
        Pagination pagination = Pagination.of(subscriberDataRepository.count(), max, pagePath, page);
        return new SubscriberListPage(messages.get("subscriberList.title", "Subscribers"),
                subscriberDataRepository.find(pageable), pagination);
    }

}
