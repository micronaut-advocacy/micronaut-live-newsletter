package com.objectcomputing.newsletter.live.newsletter.controller;

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterDetail;
import com.objectcomputing.newsletter.live.views.Model;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class NewsletterShowModel extends Model {
    @NonNull
    private final NewsletterDetail newsletter;

    public NewsletterShowModel(@NonNull NewsletterDetail newsletter) {
        this.newsletter = newsletter;
    }

    @NonNull
    public NewsletterDetail getNewsletter() {
        return newsletter;
    }
}
