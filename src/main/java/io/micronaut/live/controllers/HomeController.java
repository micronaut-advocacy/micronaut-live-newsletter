package io.micronaut.live.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;

@Controller
class HomeController {

    @Get
    HttpResponse<?> home() {
        return HttpResponse.seeOther(UriBuilder.of("/subscription").path("create").build());
    }
}
