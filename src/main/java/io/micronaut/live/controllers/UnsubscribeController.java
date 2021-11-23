package io.micronaut.live.controllers;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.live.services.ConfirmationCodeVerifier;
import io.micronaut.live.services.UnsubscribeService;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller("/unsubscribe")
class UnsubscribeController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final UnsubscribeService unsubscribeService;

    UnsubscribeController(ConfirmationCodeVerifier confirmationCodeVerifier,
                          UnsubscribeService unsubscribeService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.unsubscribeService = unsubscribeService;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.TEXT_HTML)
    @View("unsubscribed")
    @Get
    HttpResponse<?> unsubscribe(@Nullable @QueryValue String token) {
        if (StringUtils.isEmpty(token)) {
            return notFound();
        }
        Optional<String> emailOptional = confirmationCodeVerifier.verify(token);
        if (!emailOptional.isPresent()) {
            return notFound();
        }
        unsubscribeService.unsubscribe(emailOptional.get());
        return HttpResponse.ok(Collections.emptyMap());
    }

    private HttpResponse<?> notFound() {
        try {
            return HttpResponse.seeOther(new URI("/404"));
        } catch (URISyntaxException ex) {
            return HttpResponse.serverError();
        }
    }
}
