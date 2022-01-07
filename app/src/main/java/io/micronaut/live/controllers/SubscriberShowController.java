package io.micronaut.live.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.live.Subscriber;
import io.micronaut.live.services.SubscriberShowService;
import io.micronaut.live.views.HtmlPage;
import io.micronaut.live.views.SubscriberDetailPage;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Optional;

@Controller("/subscriber")
class SubscriberShowController {

    private final SubscriberShowService subscriberShowService;

    SubscriberShowController(SubscriberShowService subscriberShowService) {
        this.subscriberShowService = subscriberShowService;
    }

    @Operation(operationId = "subscriber-show",
            summary = "renders an HTML with a subscriber detail",
            description = "renders an HTML with a subscriber detail"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with a subscriber detail")
    @ExecuteOn(TaskExecutors.IO)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.TEXT_HTML)
    @Get("/{id}")
    @View("subscriberdetail")
    HtmlPage show(@PathVariable String id) {
        String title = "Detail"; //TODO i18n
        Optional<Subscriber> subscriberOptional = subscriberShowService.findById(id);
        if (!subscriberOptional.isPresent()) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "subscriber not found by id" + id);
        }
        Subscriber subscriber = subscriberOptional.get();
        return new SubscriberDetailPage(title, subscriber);
    }
}
