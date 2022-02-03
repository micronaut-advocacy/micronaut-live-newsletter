package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import jakarta.annotation.security.PermitAll;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller("/subscriber")
class SubscriberCreateController {

    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    @View("subscriptioncreate")
    @Get("/create")
    Map<String, Object> create() {
        return new HashMap<>();
    }
}
