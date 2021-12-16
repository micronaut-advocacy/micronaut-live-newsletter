package io.micronaut.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.live.model.Alert;
import io.micronaut.live.model.AlertPage;
import io.micronaut.live.services.ConfirmationCodeVerifier;
import io.micronaut.live.services.ConfirmationService;
import io.micronaut.live.services.UnsubscribeService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("/subscriber")
class SubscriberConfirmController {

    public static final String CONFIRMATION_FAILED = "Confirmation Failed";
    public static final String MODEL_KEY_TITLE = "title";
    public static final String MODEL_KEY_ALERT = "alert";
    public static final String CONFIRMATION_SUCCESS = "Confirmation Success";
    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final ConfirmationService confirmationService;

    SubscriberConfirmController(ConfirmationCodeVerifier confirmationCodeVerifier,
                                ConfirmationService confirmationService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.confirmationService = confirmationService;
    }

    @Operation(operationId = "subscriber-confirm",
            summary = "renders HTML page with the subscription confirmation result",
            description = "renders HTML page with subscription confirmation result. It renders an alter if subscription confirmation failed or a success message if the operation could be completed"
    )
    @ApiResponse(responseCode = "200",
            description = "renders HTML page with subscription confirmation result",
            content = @Content(schema = @Schema(implementation = String.class),
                    mediaType = "text/html")
    )
    @Parameters(value = @Parameter(name = "token",
            required = true,
            in = ParameterIn.QUERY,
            example = "xxx.zzz.yyy",
            schema = @Schema(implementation = String.class),
            description = "Signed token containing the user unsubscribing in the claims"))
    @Produces(MediaType.TEXT_HTML)
    @ExecuteOn(TaskExecutors.IO)
    @View("alert")
    @Get("/confirm")
    @PermitAll
    AlertPage confirm(@Nullable @QueryValue String token) {
        if (StringUtils.isEmpty(token)) {
            return new AlertPage(CONFIRMATION_FAILED,
                    Alert.builder().danger("token is required").build()); //TODO do this via i18n
        }
        Optional<String> emailOptional = confirmationCodeVerifier.verify(token);
        if (!emailOptional.isPresent()) {
            return new AlertPage(CONFIRMATION_FAILED,
                    Alert.builder().danger("could not verify the token").build()); //TODO do this via i18n
        }
        confirmationService.confirm(emailOptional.get());
        return new AlertPage(CONFIRMATION_SUCCESS,
                Alert.builder().success("thanks, we have confirmed your subscription").build());
    }

    private HttpResponse<?> notFound() {
        try {
            return HttpResponse.seeOther(new URI("/404"));
        } catch (URISyntaxException ex) {
            return HttpResponse.serverError();
        }
    }
}
