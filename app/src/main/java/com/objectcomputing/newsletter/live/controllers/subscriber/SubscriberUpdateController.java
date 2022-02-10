package com.objectcomputing.newsletter.live.controllers.subscriber;

import com.objectcomputing.newsletter.live.services.SubscriberUpdateService;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/subscriber")
class SubscriberUpdateController {

    private final SubscriberUpdateService subscriberUpdateService;

    SubscriberUpdateController(SubscriberUpdateService subscriberUpdateService) {
        this.subscriberUpdateService = subscriberUpdateService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> update(@Body @NonNull @NotNull @Valid SubscriberDetail subscriberDetail) {
        subscriberUpdateService.update(subscriberDetail);
        return HttpResponse.seeOther(UriBuilder.of("/subscriber")
                .path(subscriberDetail.getId())
                .build());
    }
}
