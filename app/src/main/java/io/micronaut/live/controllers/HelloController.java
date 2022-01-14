package io.micronaut.live.controllers;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Requires(notEnv = Environment.TEST)
@Controller
public class HelloController {

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    String index() {
        return "hello world";
    }
}
