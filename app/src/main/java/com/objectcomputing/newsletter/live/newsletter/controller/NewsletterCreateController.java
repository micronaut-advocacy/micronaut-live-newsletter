package com.objectcomputing.newsletter.live.newsletter.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboView;

@Controller
class NewsletterCreateController {

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get(NewsletterUrlMappings.PATH_NEWSLETTER_CREATE)
    @TurboView("newsletter/fragments/create")
    @View("newsletter/create")
    NewsletterCreateModel create() {
        return new NewsletterCreateModel(new NewsletterCreateForm());
    }
}
