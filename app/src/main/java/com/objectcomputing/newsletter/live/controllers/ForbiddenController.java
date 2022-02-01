package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import com.objectcomputing.newsletter.i18n.Messages;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

@Controller("/forbidden")
class ForbiddenController {

    @Operation(operationId = "forbidden",
            summary = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions",
            description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions")
    @Produces(MediaType.TEXT_HTML)
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    ModelAndView<AlertPage> forbidden(@NonNull Messages messages) {
        return new ModelAndView<>("alert", createAlertPage(messages));
    }

    @NonNull
    private AlertPage createAlertPage(@NonNull Messages messages) {
        String message = messages.get("forbidden.title", "Forbidden");
        return new AlertPage(message,
                Alert.builder().danger(message).build());
    }

}
