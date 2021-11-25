package io.micronaut.live.api.v1;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.live.services.ConfirmationCodeVerifier;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.live.api.v1.Api.SUBSCRIBER_PATH;
import static io.micronaut.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
public class SubscriberConfirmationController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;

    public SubscriberConfirmationController(ConfirmationCodeVerifier confirmationCodeVerifier) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
    }

    @Operation(operationId = "api-subscriber-confirmation",
            summary = "Confirms a subscriber",
            description = "Confirms a subscriber"
    )
    @ApiResponse(responseCode = "200",
            description = "subscriber confirmed")
    @ApiResponse(responseCode = "422",
            description = "subscriber could not be confirmed. Token maybe invalid")
    @RequestBody(content = @Content(schema = @Schema(implementation = SubscriberConfirmationRequest.class),
            mediaType = "application/json"))
    @ExecuteOn(TaskExecutors.IO)
    @Patch(SUBSCRIBER_PATH + "/confirm")
    HttpStatus confirm(@Body @NonNull @NotNull @Valid SubscriberConfirmationRequest subscriberConfirmationRequest) {
        return confirmationCodeVerifier.verify(subscriberConfirmationRequest.getToken()).isPresent() ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY;
    }


}
