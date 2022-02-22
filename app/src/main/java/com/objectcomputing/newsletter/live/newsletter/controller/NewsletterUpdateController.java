package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterEditModel;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterUpdateForm;
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterUpdateService;
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

@Controller
public class NewsletterUpdateController extends FormController<NewsletterUpdateForm> {

    private final NewsletterUpdateService newsletterUpdateService;

    NewsletterUpdateController(LocalizedMessageSource localizedMessageSource,
                               ViewsRenderer<FormModel<NewsletterUpdateForm>> viewsRenderer,
                               NewsletterUpdateService newsletterUpdateService) {
        super(localizedMessageSource, viewsRenderer);
        this.newsletterUpdateService = newsletterUpdateService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post(NewsletterUrlMappings.PATH_NEWSLETTER_UPDATE)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> update(@Body @NonNull @NotNull @Valid NewsletterUpdateForm form) {
        newsletterUpdateService.update(form);
        return HttpResponse.seeOther(NewsletterUrlMappings.show(form.getId()));
    }

    @Override
    @NonNull
    protected ModelAndView<? extends FormModel<NewsletterUpdateForm>> turboValidationFailedModelAndView(NewsletterUpdateForm form) {
        return new ModelAndView<>("newsletter/fragments/edit", new NewsletterEditModel(form));
    }

    @Override
    @NonNull
    protected ModelAndView<? extends FormModel<NewsletterUpdateForm>> validationFailedModelAndView(NewsletterUpdateForm form) {
        return new ModelAndView<>("newsletter/edit", new NewsletterEditModel(form));
    }
}
