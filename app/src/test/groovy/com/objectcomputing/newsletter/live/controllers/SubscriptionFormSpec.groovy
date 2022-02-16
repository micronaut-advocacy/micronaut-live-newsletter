package com.objectcomputing.newsletter.live.controllers

import io.micronaut.context.BeanContext
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.type.Argument
import io.micronaut.serde.SerdeIntrospections
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriptionFormSpec extends Specification {
    @Inject
    BeanContext beanContext

    void "SubscriptionForm is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriptionForm)

        then:
        noExceptionThrown()
    }

    void "SubscriptionForm is annotated with @Serdeable.Deserializable"() {
        given:
        SerdeIntrospections serdeIntrospections = beanContext.getBean(SerdeIntrospections)

        when:
        serdeIntrospections.getDeserializableIntrospection(Argument.of(SubscriptionForm))

        then:
        noExceptionThrown()
    }
}
