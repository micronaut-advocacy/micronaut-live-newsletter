package com.objectcomputing.newsletter.live.controllers.subscriber;

import com.objectcomputing.newsletter.live.controllers.HttpRequestUtils;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import com.objectcomputing.newsletter.live.views.SubscriberEditPage;
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
import com.objectcomputing.newsletter.live.services.SubscriberShowService;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;
import com.objectcomputing.newsletter.live.views.SubscriberDetailPage;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboHttpHeaders;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller("/subscriber")
class SubscriberEditController {
    private final LocalizedMessageSource messageSource;
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;
    private final SubscriberShowService subscriberShowService;

    SubscriberEditController(LocalizedMessageSource messageSource,
                             SubscriberShowService subscriberShowService,
                             ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.messageSource = messageSource;
        this.viewsRenderer = viewsRenderer;
        this.subscriberShowService = subscriberShowService;
    }

    @Operation(operationId = "subscriber-edit",
            summary = "renders an HTML FORM to edit subscriber details",
            description = "renders an HTML FORM to edit subscriber details"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML FORM to edit subscriber details")
    @ExecuteOn(TaskExecutors.IO)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get("/{id}/edit")
    HttpResponse<?> edit(@PathVariable String id,
                      HttpRequest<?> request,
                      @Nullable @Header(TURBO_FRAME) String turboFrame) {
        String title = messageSource.getMessageOrDefault("subscriber.edit", "Edit Subscriber");

        Optional<SubscriberDetail> subscriberOptional = subscriberShowService.findById(id);
        if (!subscriberOptional.isPresent()) {
            String message = messageSource.getMessageOrDefault("subscriber.notFoundById", "subscriber not found by id" + id, id);
            if (turboFrame != null) {
                return TurboResponse.ok(TurboStream
                        .builder()
                        .targetDomId(turboFrame)
                        .template(viewsRenderer.render("fragments/bootstrap/alert", Collections.singletonMap("alert", Alert.danger(message)), request))
                        .update());
            }
            if (HttpRequestUtils.acceptsHtml(request)) {
                return HttpResponse.ok(new ModelAndView<>("alert", new AlertPage(message, Alert.danger(message))));
            }
            throw new HttpStatusException(HttpStatus.NOT_FOUND, message);
        }
        SubscriberDetail subscriber = subscriberOptional.get();
        SubscriberEditForm subscriberEditForm = new SubscriberEditForm();
        subscriberEditForm.setId(subscriber.getId());
        subscriberEditForm.setEmail(subscriber.getEmail());
        subscriberEditForm.setName(subscriber.getName());
        if (turboFrame != null) {
            return TurboResponse.ok(TurboStream
                            .builder()
                            .targetDomId(turboFrame)
                            .template(viewsRenderer.render("subscriber/fragments/edit", Collections.singletonMap("subscriber", subscriberEditForm), request))
                            .update());
        }
        return HttpResponse.ok(new ModelAndView<>("subscriber/edit", new SubscriberEditPage(title, subscriberEditForm)));
    }
}
