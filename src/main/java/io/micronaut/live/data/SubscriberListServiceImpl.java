package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.Pageable;
import io.micronaut.live.conf.SubscriberConfiguration;
import io.micronaut.live.services.SubscriberListService;
import io.micronaut.live.views.SubscriberRow;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotNull;
import java.util.List;

@Singleton
public class SubscriberListServiceImpl implements SubscriberListService {

    private final SubscriberDataRepository subscriberDataRepository;
    private final SubscriberConfiguration subscriberConfiguration;

    public SubscriberListServiceImpl(SubscriberDataRepository subscriberDataRepository, SubscriberConfiguration subscriberConfiguration) {
        this.subscriberDataRepository = subscriberDataRepository;
        this.subscriberConfiguration = subscriberConfiguration;
    }

    @Override
    public List<SubscriberRow> findAll(@NonNull @NotNull Integer page) {
        return subscriberDataRepository.find(Pageable.from(page, subscriberConfiguration.getSubscriberListPageSize()));
    }
}
