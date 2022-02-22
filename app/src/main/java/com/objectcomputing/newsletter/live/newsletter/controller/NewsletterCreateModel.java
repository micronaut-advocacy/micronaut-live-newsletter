package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterSaveForm;
import com.objectcomputing.newsletter.live.views.FormModel;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class NewsletterCreateModel extends FormModel<NewsletterCreateForm> {
    public NewsletterCreateModel(@NonNull NewsletterCreateForm form) {
        setForm(form);
    }
}
