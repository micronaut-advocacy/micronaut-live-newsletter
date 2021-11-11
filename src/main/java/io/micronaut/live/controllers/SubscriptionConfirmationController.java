package io.micronaut.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.live.model.Alert;
import io.micronaut.live.services.ConfirmationCodeVerifier;
import io.micronaut.live.services.ConfirmationService;
import io.micronaut.live.services.UnsubscribeService;
import io.micronaut.views.View;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("/confirm")
class SubscriptionConfirmationController {

    public static final String CONFIRMATION_FAILED = "Confirmation Failed";
    public static final String MODEL_KEY_TITLE = "title";
    public static final String MODEL_KEY_ALERT = "alert";
    public static final String CONFIRMATION_SUCCESS = "Confirmation Success";
    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final ConfirmationService confirmationService;

    SubscriptionConfirmationController(ConfirmationCodeVerifier confirmationCodeVerifier,
                                       ConfirmationService confirmationService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.confirmationService = confirmationService;
    }

    @Produces(MediaType.TEXT_HTML)
    @View("alert")
    @Get
    Map<String, Object> confirm(@Nullable @QueryValue String token) {
        if (StringUtils.isEmpty(token)) {
            return createModel(CONFIRMATION_FAILED,
                    Alert.builder().danger("token is required").build()); //TODO do this via i18n
        }
        Optional<String> emailOptional = confirmationCodeVerifier.verify(token);
        if (!emailOptional.isPresent()) {
            return createModel(CONFIRMATION_FAILED,
                    Alert.builder().danger("could not verify the token").build()); //TODO do this via i18n
        }
        confirmationService.confirm(emailOptional.get());
        return createModel(CONFIRMATION_SUCCESS,
                Alert.builder().success("thanks, we have confirmed your subscription").build());
    }

    @NonNull
    private Map<String, Object> createModel(@NonNull String title,
                                            @NonNull Alert alert) {
        Map<String, Object> model = new HashMap<>();
        model.put(MODEL_KEY_TITLE, title);
        model.put(MODEL_KEY_ALERT, alert);
        return model;
    }

    private HttpResponse<?> notFound() {
        try {
            return HttpResponse.seeOther(new URI("/404"));
        } catch (URISyntaxException ex) {
            return HttpResponse.serverError();
        }
    }
}
