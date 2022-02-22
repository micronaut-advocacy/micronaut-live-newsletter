package com.objectcomputing.newsletter.live.newsletter.controller;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller
class NewsletterEditController {

    @Produces(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get(NewsletterUrlMappings.PATH_NEWSLETTER + "/{id}/" + NewsletterUrlMappings.PATH_EDIT)
    HttpResponse<?> edit(@PathVariable @NonNull String id) {
        return HttpResponse.ok();
    }
}
