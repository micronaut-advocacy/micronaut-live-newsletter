package com.objectcomputing.newsletter.live.views

import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.type.Argument
import io.micronaut.serde.SerdeIntrospections
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberDetailSpec extends Specification {
    @Inject
    SerdeIntrospections serdeIntrospections

    void "SubscriberDetail is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriberDetail)

        then:
        noExceptionThrown()
    }

    void "SubscriberDetail is annotated with @Serdeable.Deserializable"() {
        when:
        serdeIntrospections.getDeserializableIntrospection(Argument.of(SubscriberDetail))

        then:
        noExceptionThrown()
    }
}
