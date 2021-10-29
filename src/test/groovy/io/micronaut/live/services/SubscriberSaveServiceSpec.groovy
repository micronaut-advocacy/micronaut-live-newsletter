package io.micronaut.live.services

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.live.Subscriber
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Property(name = "spec.name", value = "SubscriberSaveServiceSpec")
@MicronautTest(startApplication = false)
class SubscriberSaveServiceSpec extends Specification {

    @Inject
    SubscriberSaveService subscriberSaveService

    void "SubscriberSaveService#save parameter cannot be null"() {
         when:
         subscriberSaveService.save(null)

        then:
        thrown(ConstraintViolationException)
    }

    @Unroll("#email is not a valid email address for the Subscriber method parameter of SubscriberSaveService::save")
    void "SubscriberSaveService::save parameter must contain subscriber with a valid email address"(String email) {
        given:
        Subscriber subscriber = new Subscriber(email, null)

        when:
        subscriberSaveService.save(subscriber)

        then:
        thrown(ConstraintViolationException)

        where:
        email << ['', null, 'tcook']
    }

    @Requires(property = "spec.name", value = "SubscriberSaveServiceSpec")
    @Replaces(SubscriberSaveService)
    @Singleton
    static class SubscriberSaveServiceReplacement implements SubscriberSaveService {

        @Override
        @NonNull
        Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber) {
            Optional.empty()
        }
    }
}
