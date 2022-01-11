package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.security.handlers.LoginHandler
import io.micronaut.security.session.SessionLoginHandler
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "micronaut.security.authentication", value = "session")
@MicronautTest(startApplication = false)
class LoginHandlerSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "There is a bean of type LoginHandler and it is SessionLoginHandler"() {
        expect:
        beanContext.containsBean(LoginHandler)

        and:
        beanContext.getBean(LoginHandler) instanceof SessionLoginHandler
    }
}
