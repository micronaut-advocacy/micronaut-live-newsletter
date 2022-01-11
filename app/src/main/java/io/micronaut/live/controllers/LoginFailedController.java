package io.micronaut.live.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.live.model.Alert;
import io.micronaut.live.model.AlertPage;
import io.micronaut.views.ModelAndView;
import jakarta.annotation.security.PermitAll;

@Controller("/login")
public class LoginFailedController {

    @Produces(MediaType.TEXT_HTML)
    @Get("/failed")
    @PermitAll
    ModelAndView<AlertPage> loginFailed() {
        return new ModelAndView<>("alert", createAlertPage());
    }

    private AlertPage createAlertPage() {
        String message = "Login Failed";
        return new AlertPage(message, //TODO do this via i18n
                Alert.builder().danger(message).build());
    }

}
