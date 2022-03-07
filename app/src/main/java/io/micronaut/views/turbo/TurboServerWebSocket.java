package io.micronaut.views.turbo;

import com.objectcomputing.newsletter.live.data.SubscriberService;
import io.micronaut.core.io.Writable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.turbo.http.TurboMediaType;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Optional;

@Secured(SecurityRule.IS_ANONYMOUS)
@ServerWebSocket("/turbo")
public class TurboServerWebSocket {
    private static final Logger LOG = LoggerFactory.getLogger(TurboServerWebSocket.class);
    private final TurboStreamRenderer turboStreamRenderer;
    private final WebSocketBroadcaster broadcaster;
    private final SubscriberService subscriberService;

    public TurboServerWebSocket(WebSocketBroadcaster broadcaster,
                                SubscriberService subscriberService,
                                TurboStreamRenderer turboStreamRenderer) {
        this.turboStreamRenderer = turboStreamRenderer;
        this.broadcaster = broadcaster;
        this.subscriberService = subscriberService;
    }

    @OnOpen
    public void onOpen(WebSocketSession session) {
        if (LOG.isInfoEnabled()) {
            LOG.info("onOpen");
        }
        TurboStream.Builder turboStream = subscriberCount();
        /*Optional<Writable> writableOptional = turboStreamRenderer.render(turboStream, null);
        if (writableOptional.isPresent()) {
            StringWriter writer = new StringWriter();
            try {
                writableOptional.get().writeTo(writer);
                broadcaster.broadcastAsync(writer.toString(), TurboMediaType.TURBO_STREAM_TYPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }

    private TurboStream.Builder subscriberCount() {
        return TurboStream.builder()
                    .targetDomId("subscribers-count")
                    .template("fragments/subscriberCount", Collections.singletonMap("count", subscriberService.countSubscribers()))
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
