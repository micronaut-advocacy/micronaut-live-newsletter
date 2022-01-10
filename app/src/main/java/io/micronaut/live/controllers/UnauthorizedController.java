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

@Controller("/unauthorized")
public class UnauthorizedController {

    @Produces(MediaType.TEXT_HTML)
    @Get
    @PermitAll
    ModelAndView<AlertPage> unauthorized() {
        return new ModelAndView<>("alert", createAlertPage());
    }

    private AlertPage createAlertPage() {
        String message = "Unauthorized";
        return new AlertPage(message, //TODO do this via i18n
                Alert.builder().danger(message).build());
    }

}
