package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.security.handlers.LogoutHandler
import io.micronaut.security.session.SessionLogoutHandler
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "micronaut.security.authentication", value = "session")
@MicronautTest(startApplication = false)
class LogoutHandlerSpec extends Specification {

    @Inject
    BeanContext beanContext
    void "There is a bean of type LogoutHandler and it is SessionLogoutHandler"() {
        expect:
        beanContext.containsBean(LogoutHandler)
        and:
        beanContext.getBean(LogoutHandler) instanceof SessionLogoutHandler
    }
}
