package io.micronaut.live.security;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.IpPatternsRule;
import io.micronaut.security.rules.SecuredAnnotationRule;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.rules.SecurityRuleResult;
import io.micronaut.web.router.RouteMatch;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

//@Singleton
public class DenyAllSecurityRule implements SecurityRule {
    public static final Integer ORDER = IpPatternsRule.ORDER - 100;

    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    public Publisher<SecurityRuleResult> check(HttpRequest<?> request, RouteMatch<?> routeMatch, Authentication authentication) {
        if (request.getPath().equals("/unauthorized")) {
            return Publishers.just(SecurityRuleResult.UNKNOWN);
        }
        return Publishers.just(SecurityRuleResult.REJECTED);
    }
}
