package com.objectcomputing.newsletter.live.controllers.newsletter;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller
class NewsletterCreateController {
    public static final String PATH_NEWSLETTER_CREATE = NewsletterListController.PATH_NEWSLETTER + "/create";

    @Produces(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get(PATH_NEWSLETTER_CREATE)
    HttpResponse<?> list() {
        return HttpResponse.ok();
    }
}
