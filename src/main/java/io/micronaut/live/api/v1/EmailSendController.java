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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import static io.micronaut.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
class EmailSendController {
    private final EmailRequestService emailRequestService;

    EmailSendController(EmailRequestService emailRequestService) {
        this.emailRequestService = emailRequestService;
    }

    @Operation(operationId = "api-email-send",
            summary = "Accepts an email request and send it asynchronously to every active subscriber",
            description = "Accepts an email request and send it asynchronously to every active subscriber"
    )
    @ApiResponse(responseCode = "202",
            description = "email request accepted")
    @RequestBody(content = @Content(schema = @Schema(implementation = EmailRequest.class),
            mediaType = "application/json"))
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
