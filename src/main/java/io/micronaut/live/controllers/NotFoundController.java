package io.micronaut.live.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.live.model.Alert;
import io.micronaut.live.model.AlertPage;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller("/404")
class NotFoundController {

    @Operation(operationId = "notfound",
            summary = "renders an HTML with alert about a page not found",
            description = "renders an HTML with alert about a page not found"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with alert about a page not found")
    @Produces(MediaType.TEXT_HTML)
    @Get
    ModelAndView<AlertPage> notFound() {
        return new ModelAndView<>("alert", createAlertPage()); //TODO do this via i18n
    }

    private AlertPage createAlertPage() {
        return new AlertPage("Not Found", //TODO do this via i18n
                Alert.builder().danger("Not Found").build());
    }
}
