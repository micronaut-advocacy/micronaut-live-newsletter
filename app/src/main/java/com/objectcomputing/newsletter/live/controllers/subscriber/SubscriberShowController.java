package com.objectcomputing.newsletter.live.controllers.subscriber;

import com.objectcomputing.newsletter.live.services.SubscriberShowService;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;
import com.objectcomputing.newsletter.live.views.SubscriberDetailPage;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.turbo.TurboStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collections;
import java.util.Optional;

import static io.micronaut.views.turbo.http.TurboHttpHeaders.TURBO_FRAME;

@Controller("/subscriber")
class SubscriberShowController {

    private final LocalizedMessageSource messageSource;
    private final SubscriberShowService subscriberShowService;
    SubscriberShowController(LocalizedMessageSource messageSource,
                             SubscriberShowService subscriberShowService) {
        this.messageSource = messageSource;
        this.subscriberShowService = subscriberShowService;
    }

    @Operation(operationId = "subscriber-show",
            summary = "renders an HTML with a subscriber detail",
            description = "renders an HTML with a subscriber detail"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with a subscriber detail")
    @ExecuteOn(TaskExecutors.IO)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    @Get("/show/{id}")
    HttpResponse<?> show(@PathVariable String id,
                  HttpRequest<?> request,
                  @Nullable @Header(TURBO_FRAME) String turboFrame) {
        Optional<SubscriberDetail> subscriberOptional = subscriberShowService.findById(id);
        if (!subscriberOptional.isPresent()) {
            String exceptionMessage = messageSource.getMessageOrDefault("subscriber.notFoundById", "subscriber not found by id" + id, id);
            throw new HttpStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
        }
        SubscriberDetail subscriber = subscriberOptional.get();
        if (turboFrame != null) {
            return HttpResponse.ok(TurboStream
                            .builder()
                            .targetDomId(turboFrame)
                            .template("subscriber/fragments/show", Collections.singletonMap("subscriber", subscriber))
                            .update());
        }
        SubscriberDetailPage page = new SubscriberDetailPage(subscriber);
        return HttpResponse.ok(new ModelAndView<>("subscriber/show", page));
    }
}
