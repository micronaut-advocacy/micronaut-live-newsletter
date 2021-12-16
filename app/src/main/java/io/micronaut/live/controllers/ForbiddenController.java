package io.micronaut.live.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.live.model.Alert;
import io.micronaut.live.model.AlertPage;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

@Controller("/forbidden")
public class ForbiddenController {

    @Operation(operationId = "forbidden",
            summary = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions",
            description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions")
    @Produces(MediaType.TEXT_HTML)
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    ModelAndView<AlertPage> notFound() {
        return new ModelAndView<>("alert", createAlertPage());
    }

    private AlertPage createAlertPage() {
        String message = "Forbidden";
        return new AlertPage(message, //TODO do this via i18n
                Alert.builder().danger(message).build());
    }

}
