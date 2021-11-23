package io.micronaut.live.api.v1;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.live.services.EmailRequestService;
import io.micronaut.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
class EmailController {
    private final EmailRequestService emailRequestService;

    EmailController(EmailRequestService emailRequestService) {
        this.emailRequestService = emailRequestService;
    }

    @Status(HttpStatus.ACCEPTED)
    @Post("/email")
    void send(@NonNull HttpRequest<?> request,
              @Body @NonNull @NotNull @Valid EmailRequest emailRequest) {
        processEmailRequest(request, emailRequest);
    }

    @Async
    void processEmailRequest(@NonNull HttpRequest<?> request,
                             @NonNull EmailRequest emailRequest) {
        emailRequestService.process(request, emailRequest);
    }
}
