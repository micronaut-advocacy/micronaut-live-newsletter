package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.StringDeleteForm;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterDeleteService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
public class NewsletterDeleteController {

    private final NewsletterDeleteService newsletterDeleteService;

    public NewsletterDeleteController(NewsletterDeleteService newsletterDeleteService) {
        this.newsletterDeleteService = newsletterDeleteService;
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post(NewsletterUrlMappings.PATH_NEWSLETTER_DELETE)
    HttpResponse<?> delete(@Body @NonNull @NotNull @Valid StringDeleteForm form) {
        newsletterDeleteService.delete(form.getId());
        return NewsletterUrlMappings.listRedirect();
    }
}
