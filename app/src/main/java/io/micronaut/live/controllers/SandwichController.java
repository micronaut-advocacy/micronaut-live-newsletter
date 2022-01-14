package io.micronaut.live.controllers;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.annotation.security.PermitAll;

@Requires(env = "sandwich")
@Controller
public class SandwichController {

    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/sandwich")
    String eat() {
        return "sandwich";
    }
}
