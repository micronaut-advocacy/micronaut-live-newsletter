package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class AuthenticationProviderSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "Multiple AuthenticationProvider exists"() {
        expect:
        beanContext.containsBean(AuthenticationProvider)
        beanContext.getBeansOfType(AuthenticationProvider).size() > 1
    }

}
