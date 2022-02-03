package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import com.objectcomputing.newsletter.i18n.Messages;
import com.objectcomputing.newsletter.live.model.SubscriptionStatus;
import com.objectcomputing.newsletter.live.services.SubscriberListService;
import com.objectcomputing.newsletter.live.views.SubscriberListPage;
import com.objectcomputing.newsletter.live.views.SubscriberRow;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

import java.util.Arrays;

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
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.TEXT_HTML)
    @Get("/list")
    @View("subscriberlist")
    SubscriberListPage index(@Nullable @QueryValue Integer page,
                             @NonNull Messages messages) {
        return subscriberListService.findAll(page != null ? page : 1, messages);
    }
}
