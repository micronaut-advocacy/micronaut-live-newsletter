package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterSaveModel;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterSaveForm;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterSaveService;
import com.objectcomputing.newsletter.live.controllers.subscriber.FormController;
import com.objectcomputing.newsletter.live.views.FormModel;
import io.micronaut.context.LocalizedMessageSource;
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
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
class NewsletterSaveController extends FormController<NewsletterSaveForm> {
    private final NewsletterSaveService newsletterSaveService;

    NewsletterSaveController(LocalizedMessageSource localizedMessageSource,
                               ViewsRenderer<FormModel<NewsletterSaveForm>> viewsRenderer,
                               NewsletterSaveService newsletterSaveService) {
        super(localizedMessageSource, viewsRenderer);
        this.newsletterSaveService = newsletterSaveService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post(NewsletterUrlMappings.PATH_NEWSLETTER_SAVE)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> save(@Body @NonNull @NotNull @Valid NewsletterSaveForm form) {
        Optional<String> idOptional = newsletterSaveService.save(form);
        if (idOptional.isPresent()) {
            return HttpResponse.seeOther(NewsletterUrlMappings.show(idOptional.get()));
        }
        return HttpResponse.serverError();
    }

    @Override
    @NonNull
    protected ModelAndView<? extends FormModel<NewsletterSaveForm>> turboValidationFailedModelAndView(NewsletterSaveForm form) {
        return new ModelAndView<>("newsletter/fragments/edit", new NewsletterSaveModel(form));
    }

    @Override
    @NonNull
    protected ModelAndView<? extends FormModel<NewsletterSaveForm>> validationFailedModelAndView(NewsletterSaveForm form) {
        return new ModelAndView<>("newsletter/edit", new NewsletterSaveModel(form));
    }
}
