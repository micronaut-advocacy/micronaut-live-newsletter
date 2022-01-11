package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.security.authentication.Authenticator
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class AuthenticationSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "A bean of Type Authenticator exists"() {
        expect:
        beanContext.containsBean(Authenticator)
    }
}
