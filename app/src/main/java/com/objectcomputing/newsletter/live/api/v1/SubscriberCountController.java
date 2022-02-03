package com.objectcomputing.newsletter.live.api.v1;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import com.objectcomputing.newsletter.live.data.SubscriberService;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

import static com.objectcomputing.newsletter.live.api.v1.Api.SUBSCRIBER_PATH;
import static com.objectcomputing.newsletter.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
class SubscriberCountController {

    private final SubscriberService subscriberService;

    SubscriberCountController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Operation(operationId = "api-subscriber-count",
            summary = "returns the number of active subscribers",
            description = "returns the number of active subscribers"
    )
    @ApiResponse(responseCode = "200",
            description = "subscriber confirmed",
            content = @Content(mediaType = "text/plain", schema =@Schema(implementation = Integer.class))
    )
    @ExecuteOn(TaskExecutors.IO)
    @Get(SUBSCRIBER_PATH + "/count")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    Integer count() {
        return subscriberService.countSubscribers();
    }
}
