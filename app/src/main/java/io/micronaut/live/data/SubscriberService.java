package io.micronaut.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.Subscriber;

import java.util.List;

public interface SubscriberService {

    @NonNull
    Integer countSubscribers();

    //TODO maybe paginate this?
    @NonNull
    List<Subscriber> findAll();
}
