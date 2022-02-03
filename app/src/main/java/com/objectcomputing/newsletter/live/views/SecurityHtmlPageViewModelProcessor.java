package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.utils.SecurityService;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.model.ViewModelProcessor;
import jakarta.inject.Singleton;

@Singleton
public class SecurityHtmlPageViewModelProcessor implements ViewModelProcessor<HtmlPage> {
    private final SecurityService securityService;

    public SecurityHtmlPageViewModelProcessor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void process(@NonNull HttpRequest<?> request, @NonNull ModelAndView<HtmlPage> modelAndView) {
        securityService.getAuthentication().ifPresent(auth ->
                modelAndView.getModel().ifPresent(m -> m.setAuthentication(auth)));
    }
}
