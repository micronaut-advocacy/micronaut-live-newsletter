package io.micronaut.live

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validator

@MicronautTest(startApplication = false)
class SubscriberSpec extends Specification {

    @Inject
    Validator validator

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
