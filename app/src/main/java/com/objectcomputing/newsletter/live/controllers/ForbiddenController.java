package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;
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
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collections;
import java.util.Map;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller("/forbidden")
class ForbiddenController {

    private final LocalizedMessageSource messageSource;
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;
    ForbiddenController(LocalizedMessageSource messageSource,
                        ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.messageSource = messageSource;
        this.viewsRenderer = viewsRenderer;
    }

    @Operation(operationId = "forbidden",
            summary = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions",
            description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with an alert about the user being authenticated but lacking necessary permissions")
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    HttpResponse<?> forbidden(HttpRequest<?> request,
                              @Nullable @Header(TURBO_FRAME) String turboFrame) {
        String message = messageSource.getMessageOrDefault("forbidden.title", "Forbidden");
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
