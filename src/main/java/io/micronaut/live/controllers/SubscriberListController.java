package io.micronaut.live.controllers;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.live.model.SubscriptionStatus;
import io.micronaut.live.services.SubscriberListService;
import io.micronaut.live.views.SubscriberListPage;
import io.micronaut.live.views.SubscriberRow;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

import java.util.Arrays;

@PermitAll // TODO don't do this
@Controller("/subscriber")
public class SubscriberListController {
    private final SubscriberListService subscriberListService;

    public SubscriberListController(SubscriberListService subscriberListService) {
        this.subscriberListService = subscriberListService;
    }

    @Operation(operationId = "subscriber-list",
            summary = "renders an HTML with list of subscribers",
            description = "renders an HTML with list of subscribers"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with list of subscribers")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.TEXT_HTML)
    @Get("/list")
    @View("subscriberlist")
    SubscriberListPage index(@Nullable @QueryValue Integer page) {
        return subscriberListService.findAll(page != null ? page : 1);
    }
}
