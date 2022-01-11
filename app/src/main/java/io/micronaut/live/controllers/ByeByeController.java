package io.micronaut.live.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.live.model.Alert;
import io.micronaut.live.model.AlertPage;
import io.micronaut.views.View;
import jakarta.annotation.security.PermitAll;

@Controller("/byebye")
class ByeByeController {

    @Get
    @PermitAll
    @View("alert")
    AlertPage bye() {
        return new AlertPage("Bye", Alert.builder().info("Bye bye").build());
    }
}
