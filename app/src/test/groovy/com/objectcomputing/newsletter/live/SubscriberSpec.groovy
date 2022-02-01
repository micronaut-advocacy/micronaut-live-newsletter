package com.objectcomputing.newsletter.live

import com.objectcomputing.newsletter.live.api.v1.EmailRequest
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.type.Argument
import io.micronaut.serde.SerdeIntrospections
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validator

@MicronautTest(startApplication = false)
class SubscriberSpec extends Specification {

    @Inject
    Validator validator

    @Inject
    SerdeIntrospections serdeIntrospections

    void "Subscriber is annotated with @Serdeable.Deserializable"() {
        when:
        serdeIntrospections.getDeserializableIntrospection(Argument.of(Subscriber))

        then:
        noExceptionThrown()
    }

    void "EmailRequest is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(EmailRequest)

        then:
        noExceptionThrown()
    }

    void "no constraint violations for valid Subscriber"() {
        given:
        Subscriber subscriber = new Subscriber("tcook@apple.com", null)

        expect:
        validator.validate(subscriber).isEmpty()
    }

    @Unroll("Subscriber email with value #email is not valid")
    void "Subscriber::email is required"(String email) {
        given:
        Subscriber subscriber = new Subscriber(email, null)

        expect:
        !validator.validate(subscriber).isEmpty()

        where:
        email << [null, ""]
    }

    void "Subscriber::email must be an email address"(String email) {
        given:
        Subscriber subscriber = new Subscriber('tcook', null)

        expect:
        !validator.validate(subscriber).isEmpty()

        where:
        email << [null, ""]
    }
}
