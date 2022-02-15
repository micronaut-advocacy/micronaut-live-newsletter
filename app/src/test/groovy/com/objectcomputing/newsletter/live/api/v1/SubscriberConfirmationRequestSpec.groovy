package com.objectcomputing.newsletter.live.api.v1

import io.micronaut.context.BeanContext
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.type.Argument
import io.micronaut.serde.SerdeIntrospections
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.PendingFeature
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberConfirmationRequestSpec extends Specification {
    @Inject
    BeanContext beanContext

    void "SubscriberConfirmationRequest is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriberConfirmationRequest)

        then:
        noExceptionThrown()
    }

    @PendingFeature
    void "SubscriberConfirmationRequest is annotated with @Serdeable.Deserializable"() {
        given:
        SerdeIntrospections serdeIntrospections = beanContext.getBean(SerdeIntrospections)

        when:
        serdeIntrospections.getDeserializableIntrospection(Argument.of(SubscriberConfirmationRequest))

        then:
        noExceptionThrown()
    }
}
