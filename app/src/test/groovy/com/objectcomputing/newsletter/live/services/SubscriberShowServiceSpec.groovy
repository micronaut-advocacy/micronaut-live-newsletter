package com.objectcomputing.newsletter.live.services

import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberShowServiceSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "there is a bean of type SubscriberShowService"() {
        expect:
        beanContext.containsBean(SubscriberShowService)
    }
}
