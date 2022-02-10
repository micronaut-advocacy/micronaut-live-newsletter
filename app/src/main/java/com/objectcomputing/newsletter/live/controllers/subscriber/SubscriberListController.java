package com.objectcomputing.newsletter.live.controllers.subscriber;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import com.objectcomputing.newsletter.live.services.SubscriberListService;
import com.objectcomputing.newsletter.live.views.SubscriberListPage;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboHttpHeaders;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

@Controller("/subscriber")
public class SubscriberListController {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriberListController.class);
    private final SubscriberListService subscriberListService;
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;

    public SubscriberListController(SubscriberListService subscriberListService,
                                    ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.subscriberListService = subscriberListService;
        this.viewsRenderer = viewsRenderer;
    }

    @Operation(operationId = "subscriber-list",
            summary = "renders an HTML with list of subscribers",
            description = "renders an HTML with list of subscribers"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with list of subscribers")
    @ExecuteOn(TaskExecutors.IO)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    @Get("/list")
    HttpResponse<?> index(@Nullable @QueryValue Integer page,
                                          HttpRequest<?> request,
                                          @Nullable @Header(TURBO_FRAME) String turboFrame) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Turbo Frame {}", turboFrame);
        }
        SubscriberListPage subscriberListPage = subscriberListService.findAll(page != null ? page : 1);
        if (turboFrame != null) {
            Map<String, Object> data = CollectionUtils.mapOf("rows", subscriberListPage.getRows(), "pagination", subscriberListPage.getPagination());
            return TurboResponse.ok(TurboStream
                    .builder()
                    .targetDomId(turboFrame)
                    .template(viewsRenderer.render("subscriber/fragments/table", data, request))
                    .update());
        }
        return HttpResponse.ok(new ModelAndView<>("subscriber/list", subscriberListPage));
    }
}
