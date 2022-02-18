package com.objectcomputing.newsletter.live.controllers.newsletter;

import com.objectcomputing.newsletter.live.views.FormModel;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class NewsletterCreateModel extends FormModel<NewsletterSaveForm> {
    public NewsletterCreateModel(@NonNull NewsletterSaveForm form) {
        setForm(form);
    }
}
