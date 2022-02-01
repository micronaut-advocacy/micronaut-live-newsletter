package com.objectcomputing.newsletter.live.services

import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification


@MicronautTest(startApplication = false)
class IdGeneratorSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "Bean of type IdGenerator exists"() {
        expect:
        beanContext.containsBean(IdGenerator)
    }
}
