package com.objectcomputing.newsletter.live.controllers.subscriber;

import com.objectcomputing.newsletter.live.services.SubscriberUpdateService;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;
import com.objectcomputing.newsletter.live.views.SubscriberEditPage;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/subscriber")
class SubscriberUpdateController extends FormController<SubscriberEditForm, SubscriberEditPage> {
    private final SubscriberUpdateService subscriberUpdateService;

    SubscriberUpdateController(LocalizedMessageSource localizedMessageSource,
                               SubscriberUpdateService subscriberUpdateService,
                               ViewsRenderer<SubscriberEditPage> viewsRenderer) {
        super(localizedMessageSource, viewsRenderer);
        this.subscriberUpdateService = subscriberUpdateService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> update(@Body @NonNull @NotNull @Valid SubscriberEditForm form) {
        subscriberUpdateService.update(new SubscriberDetail(form.getId(), form.getEmail(), form.getName()));
        return HttpResponse.seeOther(UriBuilder.of("/subscriber")
                .path(form.getId())
                .build());
    }


    @Override
    protected ModelAndView<SubscriberEditPage> validationFailedModelAndView(SubscriberEditForm form) {
        return new ModelAndView<>("subscriber/fragments/edit", new SubscriberEditPage("", form));
    }
}
