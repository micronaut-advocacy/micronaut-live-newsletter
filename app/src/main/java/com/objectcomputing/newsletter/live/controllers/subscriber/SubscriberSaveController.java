package com.objectcomputing.newsletter.live.controllers.subscriber;

import com.objectcomputing.newsletter.live.controllers.SubscriptionForm;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import com.objectcomputing.newsletter.live.Subscriber;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.services.SubscriberSaveService;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboHttpHeaders;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import jakarta.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller("/subscriber")
class SubscriberSaveController {

    private final LocalizedMessageSource messageSource;
    private final SubscriberSaveService subscriberSaveService;
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;

    SubscriberSaveController(LocalizedMessageSource messageSource,
                             SubscriberSaveService subscriberSaveService,
                             ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.messageSource = messageSource;
        this.subscriberSaveService = subscriberSaveService;
        this.viewsRenderer = viewsRenderer;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/save")
    @PermitAll
    HttpResponse<?> save(@Body @NonNull @NotNull @Valid SubscriptionForm form,
                                           HttpRequest<?> request,
                                           @Nullable @Header(TURBO_FRAME) String turboFrame) {
        subscriberSaveService.save(new Subscriber(form.getEmail(), null));
        String message = messageSource.getMessageOrDefault("subscriber.confirmation.email", "Please, check your email and confirm your subscription");
        if (turboFrame != null) {
            return TurboResponse.ok(TurboStream
                            .builder()
                            .targetDomId(turboFrame)
                            .template(viewsRenderer.render("fragments/bootstrap/alert", CollectionUtils.mapOf("text", "message", "class", "alert-info"), request))
                            .update());
        }
        Map<String, Object> model = new HashMap<>();
        model.put("title", messageSource.getMessage("subscriberSave.title", "Pending | Subscription"));
        model.put("alert", Alert
                .builder()
                .info(message)
                .build());
        return HttpResponse.ok(new ModelAndView<>("alert", model));
    }
}
