package io.micronaut.live.api.v1;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.live.services.ConfirmationCodeVerifier;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import static io.micronaut.live.api.v1.Api.SUBSCRIBER_PATH;
import static io.micronaut.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
public class SubscriberConfirmationController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;

    public SubscriberConfirmationController(ConfirmationCodeVerifier confirmationCodeVerifier) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get(SUBSCRIBER_PATH)
    HttpStatus confirm(@QueryValue String token) {
        return confirmationCodeVerifier.verify(token) ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY;
    }


}
