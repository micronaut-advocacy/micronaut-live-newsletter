package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.http.server.util.locale.HttpLocaleResolutionConfiguration;
import io.micronaut.http.uri.UriBuilder;
import jakarta.annotation.security.PermitAll;
import java.util.Locale;
import java.util.Optional;

@Controller("/lang")
class LanguagesController {

    public static final String COOKIE_PATH = "/";
    public static final long COOKIE_MAX_AGE = 60 * 60 * 24 * 28;
    private final HttpLocaleResolutionConfiguration httpLocaleResolutionConfiguration;

    public LanguagesController(HttpLocaleResolutionConfiguration httpLocaleResolutionConfiguration) {
        this.httpLocaleResolutionConfiguration = httpLocaleResolutionConfiguration;
    }

    @PermitAll
    @Get("/en")
    HttpResponse<?> en() {
        return homeRedirect(Locale.ENGLISH);
    }

    @PermitAll
    @Get("/es")
    HttpResponse<?> es() {
        return homeRedirect(Locale.forLanguageTag("es"));
    }

    private MutableHttpResponse<?> homeRedirect(@NonNull Locale locale) {
        Optional<Cookie> cookieOptional = createLocaleCookie(locale);
        if (!cookieOptional.isPresent()) {
            return homeRedirect();
        }
        return homeRedirect().cookie(cookieOptional.get());
    }

    private static MutableHttpResponse<?> homeRedirect() {
        return HttpResponse.seeOther(UriBuilder.of("/").build());
    }

    private Optional<Cookie> createLocaleCookie(@NonNull Locale locale) {
        Optional<String> cookieNameOptional = httpLocaleResolutionConfiguration.getCookieName();
        if (!cookieNameOptional.isPresent()) {
            return Optional.empty();
        }
        String cookieName = cookieNameOptional.get();
        return Optional.of(Cookie.of(cookieName, locale.toString())
                .maxAge(COOKIE_MAX_AGE)
                .path(COOKIE_PATH));
    }
}
