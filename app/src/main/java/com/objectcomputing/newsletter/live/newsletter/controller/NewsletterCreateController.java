package com.objectcomputing.newsletter.live.newsletter.controller;

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
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller
class NewsletterCreateController {

    private final ViewsRenderer<Model> viewsRenderer;

    NewsletterCreateController(ViewsRenderer<Model> viewsRenderer) {
        this.viewsRenderer = viewsRenderer;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get(NewsletterUrlMappings.PATH_NEWSLETTER_CREATE)
    HttpResponse<?> create(HttpRequest<?> request,
                           @Nullable @Header(TURBO_FRAME) String turboFrame) {

        NewsletterCreateModel model = new NewsletterCreateModel(new NewsletterCreateForm());
        if (turboFrame != null) {
            return TurboResponse.ok(TurboStream
                    .builder()
                    .targetDomId(turboFrame)
                    .template(viewsRenderer.render("newsletter/fragments/create", model, request))
                    .update());
        }
        return HttpResponse.ok(new ModelAndView("newsletter/create", model));
    }
}
