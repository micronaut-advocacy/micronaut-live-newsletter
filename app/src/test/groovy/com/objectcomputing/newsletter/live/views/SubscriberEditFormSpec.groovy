package com.objectcomputing.newsletter.live.views

import com.objectcomputing.newsletter.live.controllers.subscriber.SubscriberEditForm
import io.micronaut.context.BeanContext
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.type.Argument
import io.micronaut.serde.SerdeIntrospections
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.PendingFeature
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberEditFormSpec extends Specification {
    @Inject
    BeanContext beanContext

    void "SubscriberEditForm is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriberEditForm)

        then:
        noExceptionThrown()
    }

    @PendingFeature
    void "SubscriberEditForm is annotated with @Serdeable.Deserializable"() {
        given:
        SerdeIntrospections serdeIntrospections = beanContext.getBean(SerdeIntrospections)

        when:
        serdeIntrospections.getDeserializableIntrospection(Argument.of(SubscriberEditForm))

        then:
        noExceptionThrown()
    }
}
