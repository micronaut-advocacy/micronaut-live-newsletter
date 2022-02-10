package io.micronaut.views.turbo;

import com.objectcomputing.newsletter.live.data.SubscriberDataRepository;
import com.objectcomputing.newsletter.live.data.SubscriberService;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;

import java.util.Collections;
import java.util.Map;

@Secured(SecurityRule.IS_ANONYMOUS)
@ServerWebSocket("/turbo")
public class TurboServerWebSocket {
    private static final Logger LOG = LoggerFactory.getLogger(TurboServerWebSocket.class);
    private final ViewsRenderer<Map<String, Object>> viewsRenderer;
    private final WebSocketBroadcaster broadcaster;
    private final SubscriberService subscriberService;

    public TurboServerWebSocket(WebSocketBroadcaster broadcaster,
                                SubscriberService subscriberService,
                                ViewsRenderer<Map<String, Object>> viewsRenderer) {
        this.viewsRenderer = viewsRenderer;
        this.broadcaster = broadcaster;
        this.subscriberService = subscriberService;
    }

    @OnOpen
    public void onOpen(WebSocketSession session) {
        if (LOG.isInfoEnabled()) {
            LOG.info("onOpen");
        }
        TurboStream turboStream = subscriberCount();
        broadcaster.broadcastAsync(turboStream.toString(), TurboHttpHeaders.TURBO_STREAM_TYPE);
    }

    private TurboStream subscriberCount() {
        return TurboStream.builder()
                    .targetDomId("subscribers-count")
                    .template(viewsRenderer.render("fragments/subscriberCount",
                            Collections.singletonMap("count", subscriberService.countSubscribers()),
                            ServerRequestContext.currentRequest().orElse(null)))
                    .update();
    }

    @OnMessage
    public void onMessage(String message, WebSocketSession session) {
        if (LOG.isInfoEnabled()) {
            LOG.info("onMessage {}", message);
        }
    }

    @OnClose
    public void onClose(WebSocketSession session) {
        if (LOG.isInfoEnabled()) {
            LOG.info("onClose");
        }
    }
}
