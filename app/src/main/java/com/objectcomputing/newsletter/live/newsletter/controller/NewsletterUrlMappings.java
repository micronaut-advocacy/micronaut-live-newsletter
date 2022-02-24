package com.objectcomputing.newsletter.live.newsletter.controller;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.uri.UriBuilder;

import java.net.URI;

public final class NewsletterUrlMappings {
    public static final String PATH_NEWSLETTER = "/newsletter";

    public static final String PATH_EDIT = "edit";
    public static final String PATH_SHOW = "show";

    public static final String PATH_NEWSLETTER_SAVE = PATH_NEWSLETTER + "/save";
    public static final String PATH_NEWSLETTER_LIST = PATH_NEWSLETTER + "/list";
    public static final String PATH_NEWSLETTER_CREATE = PATH_NEWSLETTER + "/create";
    public static final String PATH_NEWSLETTER_DELETE = PATH_NEWSLETTER + "/delete";
    public static final String PATH_NEWSLETTER_SHOW = PATH_NEWSLETTER;
    public static final String  PATH_NEWSLETTER_UPDATE = PATH_NEWSLETTER + "/update";

    public static URI show(@NonNull String id) {
        return UriBuilder.of(PATH_NEWSLETTER_SHOW)
                .path(id)
                .path(PATH_SHOW)
                .build();
    }

    public static HttpResponse<?> listRedirect() {
        return HttpResponse.seeOther(list());
    }

    public static URI list() {
        return UriBuilder.of(PATH_NEWSLETTER_LIST)
                .build();
    }
}
