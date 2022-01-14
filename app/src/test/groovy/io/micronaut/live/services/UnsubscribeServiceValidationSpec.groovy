package io.micronaut.live.services

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.context.exceptions.NoSuchBeanException
import io.micronaut.core.annotation.NonNull
import io.micronaut.live.controllers.SubscriptionForm
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolationException
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Property(name = "spec.name", value = "UnsubscribeServiceValidationSpec")
@MicronautTest(startApplication = false)
class UnsubscribeServiceValidationSpec extends Specification {

    @Inject
    UnsubscribeService unsubscribeService

    @Unroll("#description is not a valid email")
    void "get bean of non existing type throws exception"(String email,
                                                          String description) {
        when:
        unsubscribeService.unsubscribe("tcook")

        then:
        thrown(ConstraintViolationException)

        where:
        email << ["tcook", null, '']
        description = email == null ? 'null' : email == '' ? 'blank' : email
    }

    void "for valid emails not ConstraintViolationException is thrown"() {
        when:
        unsubscribeService.unsubscribe("tcook@apple.com")

        then:
        noExceptionThrown()
    }

    @Requires(property = "spec.name", value = "UnsubscribeServiceValidationSpec")
    @Replaces(UnsubscribeService)
    @Singleton
    static class UnsubscribeServiceReplacement implements UnsubscribeService {

        @Override
        void unsubscribe(@NonNull @NotBlank @Email String email) {
        }
    }
}
