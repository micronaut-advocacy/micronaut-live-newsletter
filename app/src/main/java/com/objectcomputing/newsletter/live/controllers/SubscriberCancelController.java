package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import com.objectcomputing.newsletter.live.services.ConfirmationCodeVerifier;
import com.objectcomputing.newsletter.live.services.UnsubscribeService;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller("/subscriber")
class SubscriberCancelController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final UnsubscribeService unsubscribeService;

    SubscriberCancelController(ConfirmationCodeVerifier confirmationCodeVerifier,
                               UnsubscribeService unsubscribeService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.unsubscribeService = unsubscribeService;
    }

    @Operation(operationId = "subscriber-cancel",
            summary = "renders HTML page with unsubscription result",
            description = "renders HTML page with unsubscription result. It renders an alter if subscription cancelation failed or a success message if the operation could be completed"
    )
    @ApiResponse(responseCode = "200",
            description = "renders HTML page with unsubscription result",
            content = @Content(schema = @Schema(implementation = String.class),
                    mediaType = "text/html")
    )
    @Parameters(value = @Parameter(name = "token",
            required = true,
            in = ParameterIn.QUERY,
            example = "xxx.zzz.yyy",
            schema = @Schema(implementation = String.class),
            description = "Signed token containing the user unsubscribing in the claims"))
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.TEXT_HTML)
    @View("unsubscribed")
    @Get("/cancel")
    @PermitAll
    HttpResponse<?> unsubscribe(@Nullable @QueryValue String token) {
        if (StringUtils.isEmpty(token)) {
            return notFound();
        }
        Optional<String> emailOptional = confirmationCodeVerifier.verify(token);
        if (!emailOptional.isPresent()) {
            return notFound();
        }
        unsubscribeService.unsubscribe(emailOptional.get());
        return HttpResponse.ok(Collections.emptyMap());
    }

    private HttpResponse<?> notFound() {
        try {
            return HttpResponse.seeOther(new URI("/404"));
        } catch (URISyntaxException ex) {
            return HttpResponse.serverError();
        }
    }
}
