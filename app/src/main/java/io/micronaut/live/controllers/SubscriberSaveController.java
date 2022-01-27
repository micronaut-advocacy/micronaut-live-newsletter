package io.micronaut.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.i18n.Messages;
import io.micronaut.live.Subscriber;
import io.micronaut.live.model.Alert;
import io.micronaut.live.services.SubscriberSaveService;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.ModelAndView;
import jakarta.annotation.security.PermitAll;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Controller("/subscriber")
class SubscriberSaveController {

    private final SubscriberSaveService subscriberSaveService;

    SubscriberSaveController(SubscriberSaveService subscriberSaveService) {
        this.subscriberSaveService = subscriberSaveService;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/save")
    @PermitAll
    ModelAndView<Map<String, Object>> save(@Body @NonNull @NotNull @Valid SubscriptionForm form,
                                           @NonNull Messages messages) {
        subscriberSaveService.save(new Subscriber(form.getEmail(), null));
        Map<String, Object> model = new HashMap<>();
        model.put("title", messages.get("subscriberSave.title", "Pending | Subscription"));
        model.put("alert", Alert
                .builder()
                .info(messages.get("subscriberSave.checkYourEmailToConfirm", "Please, check your email and confirm your subscription"))
                .build());
        return new ModelAndView<>("alert", model);
    }
}
