package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;

public final class HttpRequestUtils {

    public static boolean acceptsHtml(@NonNull HttpRequest<?> request) {
        return accepts(request, MediaType.TEXT_HTML);
    }

    public static boolean accepts(@NonNull HttpRequest<?> request, @NonNull String expectedMediaType) {
        return request.getHeaders()
                .accept()
                .stream()
                .anyMatch(mediaType -> mediaType.getName().contains(expectedMediaType));
    }
}
