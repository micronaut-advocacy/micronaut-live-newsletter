package com.objectcomputing.newsletter.live.api.v1

import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.type.Argument
import io.micronaut.serde.SerdeIntrospections
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberConfirmationRequestSpec extends Specification {
    @Inject
    SerdeIntrospections serdeIntrospections

    void "SubscriberConfirmationRequest is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriberConfirmationRequest)

        then:
        noExceptionThrown()
    }

    void "SubscriberConfirmationRequest is annotated with @Serdeable.Deserializable"() {
        when:
        serdeIntrospections.getDeserializableIntrospection(Argument.of(SubscriberConfirmationRequest))

        then:
        noExceptionThrown()
    }
}
