package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterListService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterPaginatedList;
import com.objectcomputing.newsletter.live.views.Model;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboView;

@Controller
class NewsletterListController {

    public static final String VIEW_LIST = "newsletter/list";
    public static final String VIEW_LIST_TABLE = "newsletter/fragments/list";

    private final NewsletterListService newsletterListService;

    NewsletterListController(NewsletterListService newsletterListService) {
        this.newsletterListService = newsletterListService;
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get(NewsletterUrlMappings.PATH_NEWSLETTER_LIST)
    @View(VIEW_LIST)
    @TurboView(VIEW_LIST_TABLE)
    NewsletterPaginatedList list(@Nullable @QueryValue Integer page) {
        return newsletterListService.findAll(page != null ? page : 1);
    }
}
