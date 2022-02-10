package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboHttpHeaders;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import jakarta.annotation.security.PermitAll;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller("/subscriber")
class SubscriberCreateController {
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;

    SubscriberCreateController(ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.viewsRenderer = viewsRenderer;
    }

    @PermitAll
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get("/create")
    HttpResponse<?> create(HttpRequest<?> request,
                           @Nullable @Header(TURBO_FRAME) String turboFrame) {
        if (turboFrame != null) {
            return TurboResponse.ok(TurboStream
                            .builder()
                            .targetDomId(turboFrame)
                            .template(viewsRenderer.render("subscriber/fragments/create", Collections.emptyMap(), request))
                            .update());
        }
        return HttpResponse.ok(new ModelAndView("subscriber/create", new HashMap<>()));
    }
}
