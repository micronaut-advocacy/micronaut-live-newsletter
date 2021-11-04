package io.micronaut.live.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import java.util.Collections;
import java.util.Map;

@Controller("/404")
class NotFoundController {

    @Produces(MediaType.TEXT_HTML)
    @View("notfound")
    @Get
    Map<String, Object> notFound() {
        return Collections.emptyMap();
    }
}
