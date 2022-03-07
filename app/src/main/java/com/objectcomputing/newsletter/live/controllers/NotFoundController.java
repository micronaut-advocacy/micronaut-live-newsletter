package com.objectcomputing.newsletter.live.controllers;

import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

import static io.micronaut.views.turbo.http.TurboHttpHeaders.TURBO_FRAME;

@Controller("/404")
class NotFoundController {

    private final LocalizedMessageSource messageSource;
    NotFoundController(LocalizedMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Operation(operationId = "notfound",
            summary = "renders an HTML with alert about a page not found",
            description = "renders an HTML with alert about a page not found"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with alert about a page not found")
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get
    @PermitAll
    @TurboView("fragments/bootstrap/alert")
    @View("alert")
    AlertPage notFound(HttpRequest<?> request,
                          @Nullable @Header(TURBO_FRAME) String turboFrame) {
        String message = messageSource.getMessageOrDefault("notFound.title", "Not Found");
        Alert alert = Alert.danger(message);
        return new AlertPage(alert);
    }
}
