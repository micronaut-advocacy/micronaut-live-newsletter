package io.micronaut.live.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
class HomeController {

    @Operation(operationId = "home",
            summary = "Apex path which redirects to /subscriber/create",
            description = "Apex path which redirects to /subscriber/create"
    )
    @ApiResponse(responseCode = "303", description = "redirection to /subscriber/create")
    @Get
    HttpResponse<?> home() {
        return HttpResponse.seeOther(UriBuilder.of("/subscriber").path("create").build());
    }
}
