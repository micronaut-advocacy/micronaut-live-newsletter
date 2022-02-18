package com.objectcomputing.newsletter.live.controllers.newsletter;

import com.objectcomputing.newsletter.live.views.FormModel;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class NewsletterEditModel extends FormModel<NewsletterUpdateForm> {

    public NewsletterEditModel(@NonNull NewsletterUpdateForm form) {
        setForm(form);
    }
}
