package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.controllers.HttpRequestUtils;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterEditService;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterUpdateForm;
import com.objectcomputing.newsletter.live.views.Model;
import io.micronaut.core.annotation.NonNull;
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
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.micronaut.views.turbo.TurboHttpHeaders;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import io.micronaut.views.ModelAndView;
import java.util.Optional;
import io.micronaut.context.LocalizedMessageSource;
import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;
import io.micronaut.views.ViewsRenderer;
@Controller
class NewsletterEditController {

    private final LocalizedMessageSource messageSource;
    private final ViewsRenderer<Model> viewsRenderer;
    private final NewsletterEditService newsletterEditService;

    NewsletterEditController(LocalizedMessageSource messageSource,
                             NewsletterEditService newsletterEditService,
                             ViewsRenderer<Model> viewsRenderer) {
        this.messageSource = messageSource;
        this.viewsRenderer = viewsRenderer;
        this.newsletterEditService = newsletterEditService;
    }

    @Operation(operationId = "newsletter-edit",
            summary = "renders an HTML FORM to edit newsletter details",
            description = "renders an HTML FORM to edit newsletter details"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML FORM to edit newsletter details")
    @ExecuteOn(TaskExecutors.IO)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get(NewsletterUrlMappings.PATH_NEWSLETTER + "/{id}/" + NewsletterUrlMappings.PATH_EDIT)
    HttpResponse<?> edit(@PathVariable String id,
                         HttpRequest<?> request,
                         @Nullable @Header(TURBO_FRAME) String turboFrame) {
        Optional<NewsletterUpdateForm> formOptional = newsletterEditService.edit(id);
        if (!formOptional.isPresent()) {
            String message = messageSource.getMessageOrDefault("subscriber.notFoundById", "newsletter not found by id" + id, id);
            if (turboFrame != null) {
                return TurboResponse.ok(TurboStream
                        .builder()
                        .targetDomId(turboFrame)
                        .template(viewsRenderer.render("fragments/bootstrap/alert", new AlertPage(Alert.danger(message)), request))
                        .update());
            }
            if (HttpRequestUtils.acceptsHtml(request)) {
                return HttpResponse.ok(new ModelAndView<>("alert", new AlertPage(Alert.danger(message))));
            }
            throw new HttpStatusException(HttpStatus.NOT_FOUND, message);
        }
        NewsletterUpdateForm form = formOptional.get();
        if (turboFrame != null) {
            return TurboResponse.ok(TurboStream
                    .builder()
                    .targetDomId(turboFrame)
                    .template(viewsRenderer.render("newsletter/fragments/edit", new NewsletterEditModel(form), request))
                    .update());
        }
        return HttpResponse.ok(new ModelAndView<>("newsletter/edit",  new NewsletterEditModel(form)));
    }
}
