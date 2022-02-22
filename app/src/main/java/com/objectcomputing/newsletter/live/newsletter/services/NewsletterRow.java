package com.objectcomputing.newsletter.live.newsletter.services;

import com.objectcomputing.newsletter.live.newsletter.controller.NewsletterUrlMappings;
import com.objectcomputing.newsletter.live.views.NamedRow;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import java.util.Optional;

@Introspected
public class NewsletterRow extends NamedRow {

    public NewsletterRow(String id, String name) {
        super(id, name);
    }

    @Override
    @NonNull
    public Optional<String> link() {
        return Optional.of(NewsletterUrlMappings.show(getId()).toString());
    }
}
