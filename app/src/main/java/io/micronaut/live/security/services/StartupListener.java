package io.micronaut.live.security.services;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;

import java.util.Collections;

@Requires(env = Environment.DEVELOPMENT)
@Singleton
public class StartupListener implements ApplicationEventListener<ServerStartupEvent> {
    private final RegisterService registerService;

    public StartupListener(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        registerService.register("sergio.delamo@softamo.com", "admin", Collections.singletonList("ROLE_ADMIN"));
    }
}
