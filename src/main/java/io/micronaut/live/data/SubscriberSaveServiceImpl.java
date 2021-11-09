package io.micronaut.live.data;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;
import io.micronaut.live.events.SubscriptionPendingEvent;
import io.micronaut.live.services.IdGenerator;
import io.micronaut.live.services.SubscriberSaveService;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class SubscriberSaveServiceImpl implements SubscriberSaveService {
    private final IdGenerator idGenerator;
    private final SubscriberDataRepository subscriberDataRepository;
    private final ApplicationEventPublisher<SubscriptionPendingEvent> eventPublisher;

    public SubscriberSaveServiceImpl(IdGenerator idGenerator,
                                     SubscriberDataRepository subscriberDataRepository,
                                     ApplicationEventPublisher<SubscriptionPendingEvent> eventPublisher) {
        this.idGenerator = idGenerator;
        this.subscriberDataRepository = subscriberDataRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @NonNull
    public Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber) {
        return idGenerator.generate().map(id -> {
            SubscriberEntity entity = new SubscriberEntity(id, subscriber.getEmail(), subscriber.getName());
            subscriberDataRepository.save(entity);
            eventPublisher.publishEvent(new SubscriptionPendingEvent(subscriber.getEmail()));
            return id;
        });
    }
}
