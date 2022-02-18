package com.objectcomputing.newsletter.live.controllers.newsletter;

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
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
public class NewsletterSaveController extends FormController<NewsletterSaveForm> {
    public static final String PATH_NEWSLETTER_SAVE = NewsletterListController.PATH_NEWSLETTER + "/save";
    private final NewsletterSaveService newsletterSaveService;

    NewsletterSaveController(LocalizedMessageSource localizedMessageSource,
                               ViewsRenderer<FormModel<NewsletterSaveForm>> viewsRenderer,
                               NewsletterSaveService newsletterSaveService) {
        super(localizedMessageSource, viewsRenderer);
        this.newsletterSaveService = newsletterSaveService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post(PATH_NEWSLETTER_SAVE)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> update(@Body @NonNull @NotNull @Valid NewsletterSaveForm form) {
        Optional<String> idOptional = newsletterSaveService.save(form);
        if (idOptional.isPresent()) {
            return HttpResponse.seeOther(UriBuilder.of(NewsletterListController.PATH_NEWSLETTER)
                    .path("/show")
                    .path(idOptional.get())
                    .build());
        }
        return HttpResponse.serverError();
    }

    @Override
    @NonNull
    protected ModelAndView<? extends FormModel<NewsletterSaveForm>> turboValidationFailedModelAndView(NewsletterSaveForm form) {
        return new ModelAndView<>("newsletter/fragments/edit", new NewsletterCreateModel(form));
    }

    @Override
    @NonNull
    protected ModelAndView<? extends FormModel<NewsletterSaveForm>> validationFailedModelAndView(NewsletterSaveForm form) {
        return new ModelAndView<>("newsletter/edit", new NewsletterCreateModel(form));
    }
}
