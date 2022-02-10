package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboHttpHeaders;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller("/subscriber")
class SubscriberImportCreateController {
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;

    SubscriberImportCreateController(ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.viewsRenderer = viewsRenderer;
    }

    @Operation(operationId = "subscriber-import.html-create",
            summary = "renders HTML page with a form to submit a CSV file",
            description = "renders HTML page with a form to submit a CSV file"
    )
    @ApiResponse(responseCode = "200",
            description = "renders HTML page with a form to submit a CSV file",
            content = @Content(schema = @Schema(implementation = String.class),
                    mediaType = "text/html")
    )
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get("/import")
    HttpResponse<?> create(HttpRequest<?> request,
            @Nullable @Header(TURBO_FRAME) String turboFrame) {
        if (turboFrame != null) {
            return TurboResponse.ok(TurboStream
                            .builder()
                            .targetDomId(turboFrame)
                            .template(viewsRenderer.render("subscriber/fragments/import", Collections.emptyMap(), request))
                            .update());
        }
        return HttpResponse.ok(new ModelAndView("subscriber/import", new HashMap<>()));
    }
}
