package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboView;
import jakarta.annotation.security.PermitAll;

@Controller("/subscriber")
class SubscriberCreateController {

    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    @Get("/create")
    @View("subscriber/create")
    @TurboView("subscriber/fragments/create")
    void create() {

    }
}
