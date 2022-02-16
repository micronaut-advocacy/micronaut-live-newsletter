package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import com.objectcomputing.newsletter.live.services.ConfirmationCodeVerifier;
import com.objectcomputing.newsletter.live.services.ConfirmationService;
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
import java.util.Optional;

@Controller("/subscriber")
class SubscriberConfirmController {

    public static final String CONFIRMATION_FAILED = "Confirmation Failed";
    public static final String MODEL_KEY_TITLE = "title";
    public static final String MODEL_KEY_ALERT = "alert";
    public static final String CONFIRMATION_SUCCESS = "Confirmation Success";
    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final ConfirmationService confirmationService;
    private final LocalizedMessageSource messageSource;

    SubscriberConfirmController(ConfirmationCodeVerifier confirmationCodeVerifier,
                                ConfirmationService confirmationService,
                                LocalizedMessageSource messageSource) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.confirmationService = confirmationService;
        this.messageSource = messageSource;
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
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @ExecuteOn(TaskExecutors.IO)
    @View("alert")
    @Get("/confirm")
    @PermitAll
    AlertPage confirm(@Nullable @QueryValue String token) {
        if (StringUtils.isEmpty(token)) {
            return new AlertPage(
                    Alert.builder()
                            .danger(messageSource.getMessageOrDefault("subscriberConfirm.token.notBlank", "token is required"))
                            .build());
        }
        Optional<String> emailOptional = confirmationCodeVerifier.verify(token);
        if (!emailOptional.isPresent()) {
            return new AlertPage(
                    Alert.builder()
                            .danger(messageSource.getMessageOrDefault("subscriberConfirm.token.invalid", "Could not verify the token"))
                            .build());
        }
        confirmationService.confirm(emailOptional.get());
        return new AlertPage(
                Alert.builder()
                        .success(messageSource.getMessageOrDefault("subscriber.confirmation.acknowledge", "Thanks, we have confirmed your subscription"))
                        .build());
    }

    private HttpResponse<?> notFound() {
        try {
            return HttpResponse.seeOther(new URI("/404"));
        } catch (URISyntaxException ex) {
            return HttpResponse.serverError();
        }
    }
}
