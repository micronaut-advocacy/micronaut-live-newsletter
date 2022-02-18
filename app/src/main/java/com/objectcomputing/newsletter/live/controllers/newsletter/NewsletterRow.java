package com.objectcomputing.newsletter.live.controllers.newsletter;

import com.objectcomputing.newsletter.live.views.NamedRow;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.uri.UriBuilder;
import java.util.Optional;

@Introspected
public class NewsletterRow extends NamedRow {

    public NewsletterRow(String id, String name) {
        super(id, name);
    }

    @Override
    @NonNull
    public Optional<String> link() {
        return Optional.of(UriBuilder.of(NewsletterShowController.PATH_NEWSLETTER_SHOW).path(getId()).build().toString());
    }
}
