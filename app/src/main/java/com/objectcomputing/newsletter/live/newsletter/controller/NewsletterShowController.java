package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterDetail;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterShowService;
import com.objectcomputing.newsletter.live.views.Model;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.turbo.TurboStream;
import java.util.Optional;

import static io.micronaut.views.turbo.http.TurboHttpHeaders.TURBO_FRAME;

@Controller
public class NewsletterShowController {

    private final NewsletterShowService newsletterShowService;
    private final LocalizedMessageSource messageSource;

    NewsletterShowController(NewsletterShowService newsletterShowService,
                             LocalizedMessageSource messageSource) {
        this.newsletterShowService = newsletterShowService;
        this.messageSource = messageSource;
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get(NewsletterUrlMappings.PATH_NEWSLETTER + "/{id}/" + NewsletterUrlMappings.PATH_SHOW)
    HttpResponse<?> show(@PathVariable String id,
                         HttpRequest<?> request,
                         @Nullable @Header(TURBO_FRAME) String turboFrame) {
        Optional<NewsletterDetail> newsletterOptional = newsletterShowService.find(id);
        if (!newsletterOptional.isPresent()) {
            String exceptionMessage = messageSource.getMessageOrDefault("newsletter.notfound", "newsletter not found by id" + id, id);
            throw new HttpStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
        }
        NewsletterShowModel model = new NewsletterShowModel(newsletterOptional.get());
        if (turboFrame != null) {
            return HttpResponse.ok(TurboStream.builder()
                    .template("newsletter/fragments/show", model)
                    .targetDomId(turboFrame)
                    .update());
        }
        return HttpResponse.ok(new ModelAndView<>("newsletter/show", model));
    }
}
