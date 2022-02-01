package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import com.objectcomputing.newsletter.i18n.Messages;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

@Controller("/404")
class NotFoundController {

    @Operation(operationId = "notfound",
            summary = "renders an HTML with alert about a page not found",
            description = "renders an HTML with alert about a page not found"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with alert about a page not found")
    @Produces(MediaType.TEXT_HTML)
    @Get
    @PermitAll
    ModelAndView<AlertPage> notFound(Messages messages) {
        return new ModelAndView<>("alert", createAlertPage(messages));
    }

    private static AlertPage createAlertPage(Messages messages) {
        String message = messages.get("notFound.title", "Not Found");
        return new AlertPage(message,
                Alert.builder().danger(message).build());
    }
}
