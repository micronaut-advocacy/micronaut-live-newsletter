package io.micronaut.live.services

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.context.exceptions.NoSuchBeanException
import io.micronaut.live.controllers.SubscriptionForm
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification

@MicronautTest(startApplication = false)
class UnsubscribeServiceSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "bean of type UnsubscribeService exists"() {
       expect:
       beanContext.containsBean(UnsubscribeService)
    }
}
