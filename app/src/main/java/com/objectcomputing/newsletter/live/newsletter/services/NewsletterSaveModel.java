package com.objectcomputing.newsletter.live.newsletter.services;

import com.objectcomputing.newsletter.live.views.FormModel;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class NewsletterSaveModel extends FormModel<NewsletterSaveForm> {
    public NewsletterSaveModel(@NonNull NewsletterSaveForm form) {
        setForm(form);
    }
}
