package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterListService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterPaginatedList;
import com.objectcomputing.newsletter.live.views.Model;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import io.micronaut.views.turbo.TurboStreamUtils;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller
class NewsletterListController {

    public static final String VIEW_LIST = "newsletter/list";
    public static final String VIEW_LIST_TABLE = "newsletter/fragments/list";

    private final ViewsRenderer<Model> viewsRenderer;
    private final NewsletterListService newsletterListService;

    NewsletterListController(ViewsRenderer<Model> viewsRenderer,
                             NewsletterListService newsletterListService) {
        this.viewsRenderer = viewsRenderer;
        this.newsletterListService = newsletterListService;
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get(NewsletterUrlMappings.PATH_NEWSLETTER_LIST)
    HttpResponse<?> list(@Nullable @QueryValue Integer page,
                         HttpRequest<?> request,
                         @Nullable @Header(TURBO_FRAME) String turboFrame) {
        NewsletterPaginatedList model = newsletterListService.findAll(page != null ? page : 1);
        if (TurboStreamUtils.supportsTurboStream(request)) {
            return TurboResponse.ok(TurboStream
                    .builder()
                    .targetDomId(turboFrame)
                    .template(viewsRenderer.render(VIEW_LIST_TABLE, model, request))
                    .update());
        }
        return HttpResponse.ok(new ModelAndView<>(VIEW_LIST, model));
    }
}
