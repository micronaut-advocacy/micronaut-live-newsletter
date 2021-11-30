package io.micronaut.live.data;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;
import io.micronaut.live.events.SubscriptionPendingEvent;
import io.micronaut.live.model.SubscriptionStatus;
import io.micronaut.live.services.IdGenerator;
import io.micronaut.live.services.SubscriberSaveService;
import jakarta.inject.Singleton;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Override
    public void saveActiveSubscribers(@NonNull @NotNull Collection<Subscriber> subscribers) {
        subscriberDataRepository.saveAll(createActiveSubscriberEntities(subscribers));
    }

    private List<SubscriberEntity> createActiveSubscriberEntities(@NonNull Collection<Subscriber> subscribers) {
        List<SubscriberEntity> entityList = new ArrayList<>();
        for (Subscriber subscriber : subscribers) {
            createActiveSubscriberEntity(subscriber)
                    .ifPresent(entityList::add);
        }
        return entityList;
    }

    @NonNull
    private Optional<SubscriberEntity> createActiveSubscriberEntity(@NonNull Subscriber subscriber) {
        Optional<String> idOptional = idGenerator.generate();
        if (idOptional.isEmpty()) {
            return Optional.empty();
        }
        String id = idOptional.get();
        return Optional.of(new SubscriberEntity(id,
                subscriber.getEmail(),
                subscriber.getName(),
                SubscriptionStatus.ACTIVE));
    }
}
