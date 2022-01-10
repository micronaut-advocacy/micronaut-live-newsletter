package io.micronaut.live.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.live.model.Alert;
import io.micronaut.live.model.AlertPage;
import io.micronaut.live.views.HtmlPage;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.View;
import jakarta.annotation.security.PermitAll;

@Controller("/auth")
public class AuthController {

    @Produces(MediaType.TEXT_HTML)
    @Get
    @PermitAll
    @View("auth")
    HtmlPage auth() {
        return new HtmlPage("Login");
    }
}
