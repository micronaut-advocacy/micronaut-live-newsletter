package com.objectcomputing.newsletter.live.controllers;

import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller("/forbidden")
class ForbiddenController {

    private final LocalizedMessageSource messageSource;
    ForbiddenController(LocalizedMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Operation(operationId = "forbidden",
            summary = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions",
            description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions")
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @TurboView("fragments/bootstrap/alert")
    @View("alert")
    AlertPage forbidden() {
        String message = messageSource.getMessageOrDefault("forbidden.title", "Forbidden");
        Alert alert = Alert.danger(message);
        return new AlertPage(alert);
    }

}
