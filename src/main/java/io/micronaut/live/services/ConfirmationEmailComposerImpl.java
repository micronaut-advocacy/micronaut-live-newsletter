package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Singleton
public class ConfirmationEmailComposerImpl implements ConfirmationEmailComposer {
    private final ConfirmationCodeGenerator confirmationCodeGenerator;
    private final HttpHostResolver httpHostResolver;

    public ConfirmationEmailComposerImpl(ConfirmationCodeGenerator confirmationCodeGenerator,
                                         HttpHostResolver httpHostResolver) {
        this.confirmationCodeGenerator = confirmationCodeGenerator;
        this.httpHostResolver = httpHostResolver;
    }

    @Override
    @NonNull
    public String composeText(@NonNull @NotBlank String email) {
        //TODO Load this from i18n properties file or similar
        return String.join("\n", Arrays.asList("Thanks for subscribing!",
                "Please, confirm your email address by clicking the following link:",
                confirmationLink(email),
                "If you did not subscribe to the newsletter, please ignore this email"));
    }

    @NonNull
    private String confirmationLink(@NonNull String email) {
        return UriBuilder.of(ServerRequestContext.currentRequest()
                        .map(httpHostResolver::resolve)
                        .orElse(""))
                .path("confirm")
                .queryParam("token", confirmationCodeGenerator.generate(email).orElse(""))
                .build()
                .toString();
    }
}
