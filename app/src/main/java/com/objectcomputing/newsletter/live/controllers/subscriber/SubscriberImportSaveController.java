package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.multipart.CompletedFileUpload;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import com.objectcomputing.newsletter.live.services.SubscriberParserService;
import com.objectcomputing.newsletter.live.services.SubscriberSaveService;
import io.micronaut.scheduling.annotation.Async;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@Controller("/subscriber")
public class SubscriberImportSaveController {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriberImportSaveController.class);
    private final SubscriberParserService subscriberParserService;
    private final SubscriberSaveService subscriberSaveService;

    public SubscriberImportSaveController(SubscriberParserService subscriberParserService,
                                          SubscriberSaveService subscriberSaveService) {
        this.subscriberParserService = subscriberParserService;
        this.subscriberSaveService = subscriberSaveService;
    }

    @Operation(operationId = "subscriber-import.html-save",
            summary = "receives a form submission with a CSV file and renders HTML page acknowledging the user that the request is being processed",
            description = "receives a form submission with a CSV file and renders HTML page acknowledging the user that the request is being processed"
    )
    @ApiResponse(responseCode = "200",
            description = "HTML page acknowledging the user that the request is being processed",
            content = @Content(schema = @Schema(implementation = String.class),
                    mediaType = "text/html")
    )
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @View("alert")
    @Post("/import")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    AlertPage save(CompletedFileUpload file) {
        importFile(file);
        String message = "Thanks, we are importing your subscribers";
        return new AlertPage(Alert.builder().info(message).build());
    }

    @Async
    void importFile(CompletedFileUpload file) {
        try {
            subscriberParserService.parseSubscribers(file.getInputStream())
                    .ifPresent(subscriberSaveService::saveActiveSubscribers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
