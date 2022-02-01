package com.objectcomputing.newsletter.live.api.v1

import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import javax.validation.Validator

@MicronautTest(startApplication = false)
class EmailRequestSpec extends Specification {
    @Inject
    Validator validator


    void "EmailRequest is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(EmailRequest)

        then:
        noExceptionThrown()
    }

    void "valid EmailRequest does not thrown any ConstraintViolationException"() {
        given:
        String subject = "[Groovy Calamari] 179 - Groovy Calamari Returns"
        EmailRequest body = new EmailRequest(subject, "blbabalba", "blbabalba")

        expect:
        validator.validate(body).isEmpty()
    }
}
