package io.micronaut.live.security;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.scheduling.annotation.Async;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.event.LoginSuccessfulEvent;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LoginSuccessfulEventListener implements ApplicationEventListener<LoginSuccessfulEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(LoginSuccessfulEventListener.class);

    @Override
    public void onApplicationEvent(LoginSuccessfulEvent event) {
        if (event.getSource() instanceof Authentication) {
            handleLogin((Authentication) event.getSource());
        }
    }

    @Async
    protected void handleLogin(Authentication authentication) {
        if (LOG.isInfoEnabled()) {
            LOG.info("user {} logged in with roles: {}", authentication.getName(), String.join(",", authentication.getRoles()));
        }
    }
}
