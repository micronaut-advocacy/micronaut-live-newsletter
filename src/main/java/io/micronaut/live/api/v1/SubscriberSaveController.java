package io.micronaut.live.api.v1;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.live.Subscriber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/api/v1")
class SubscriberSaveController {

    @Post("/subscriber")
    @Status(HttpStatus.CREATED)
    void save(@NonNull @NotNull @Valid Subscriber subscriber) {

    }
}
