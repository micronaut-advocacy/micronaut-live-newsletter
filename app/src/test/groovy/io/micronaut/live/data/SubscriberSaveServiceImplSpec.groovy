package io.micronaut.live.data

import io.micronaut.live.Subscriber
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberSaveServiceImplSpec extends Specification implements PostgresTestPropertyProvider {
    @Inject
    SubscriberSaveServiceImpl subscriberSaveService

    @Inject
    SubscriberDataRepository subscriberDataRepository

    void "SubscriberSaveServiceImpl save a subscriber to the database"() {
        given:
        Subscriber subscriber = new Subscriber("tcook@apple.com", null)

        when:
        Optional<String> idOptional = subscriberSaveService.save(subscriber)

        then:
        subscriberDataRepository.count() == old(subscriberDataRepository.count()) + 1
        idOptional.isPresent()

        cleanup:
        subscriberDataRepository.deleteById(idOptional.get())
    }

}
