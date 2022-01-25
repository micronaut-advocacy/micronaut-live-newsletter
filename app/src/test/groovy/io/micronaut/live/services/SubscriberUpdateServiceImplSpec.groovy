package io.micronaut.live.services

import io.micronaut.live.data.PostgresTestPropertyProvider
import io.micronaut.live.data.SubscriberDataRepository
import io.micronaut.live.data.SubscriberEntity
import io.micronaut.live.views.SubscriberDetail
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(transactional = false, startApplication = false)
class SubscriberUpdateServiceImplSpec extends Specification implements PostgresTestPropertyProvider {

    @Inject
    SubscriberUpdateServiceImpl subscriberUpdateService

    @Inject
    SubscriberDataRepository subscriberDataRepository

    @Inject
    IdGenerator idGenerator

    void "SubscriberUpdateServiceImpl updates the subscriber name and email"() {
        given:
        String name = "Tim Cook"

        when:
        SubscriberEntity entity = subscriberDataRepository.save(new SubscriberEntity(idGenerator.generate().get(), "tcook@apple.com", null))
        then:
        entity.name == null

        when:
        subscriberUpdateService.update(new SubscriberDetail(entity.getId(), entity.getEmail(), name))

        then:
        name == subscriberDataRepository.findById(entity.getId()).get().getName()

        cleanup:
        subscriberDataRepository.delete(entity)
    }
}
