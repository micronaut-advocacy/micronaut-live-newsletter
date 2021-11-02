package io.micronaut.live.api.v1;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.live.data.SubscriberCountService;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import static io.micronaut.live.api.v1.Api.SUBSCRIBER_PATH;
import static io.micronaut.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
class SubscriberCountController {

    private final SubscriberCountService subscriberCountService;

    SubscriberCountController(SubscriberCountService subscriberCountService) {
        this.subscriberCountService = subscriberCountService;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get(SUBSCRIBER_PATH + "/count")
    @Produces(MediaType.TEXT_PLAIN)
    Integer count() {
        return subscriberCountService.countConfirmedSubscribers();
    }


}
