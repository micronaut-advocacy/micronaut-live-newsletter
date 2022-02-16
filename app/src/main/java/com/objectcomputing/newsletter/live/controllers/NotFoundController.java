package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

import java.util.Collections;
import java.util.Map;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller("/404")
class NotFoundController {

    private final LocalizedMessageSource messageSource;
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;
    NotFoundController(LocalizedMessageSource messageSource,
                       ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.messageSource = messageSource;
        this.viewsRenderer = viewsRenderer;
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
    HttpResponse<?> notFound(HttpRequest<?> request,
                          @Nullable @Header(TURBO_FRAME) String turboFrame) {
        String message = messageSource.getMessageOrDefault("notFound.title", "Not Found");
        Alert alert = Alert.danger(message);
        if (turboFrame != null) {
            return TurboResponse.ok(TurboStream
                    .builder()
                    .targetDomId(turboFrame)
                    .template(viewsRenderer.render("fragments/bootstrap/alert", Collections.singletonMap("alert", alert), request))
                    .update());
        }
        return HttpResponse.ok(new ModelAndView<>("alert", new AlertPage(alert)));
    }
}
