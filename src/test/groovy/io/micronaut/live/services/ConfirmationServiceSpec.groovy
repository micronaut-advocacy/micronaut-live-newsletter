package io.micronaut.live.services

import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class ConfirmationServiceSpec extends Specification {
    @Inject
    BeanContext beanContext

    void "bean of type ConfirmationService exists"() {
        expect:
        beanContext.containsBean(ConfirmationService)
    }
}
