package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller("/subscriber")
class SubscriberImportCreateController {

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
    @View("subscriber/import")
    @TurboView("subscriber/fragments/import")
    void create() {
    }
}
