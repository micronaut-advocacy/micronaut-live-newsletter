package com.objectcomputing.newsletter.live.services

import com.objectcomputing.newsletter.live.data.PostgresTestPropertyProvider
import com.objectcomputing.newsletter.live.data.SubscriberDataRepository
import com.objectcomputing.newsletter.live.data.SubscriberEntity
import com.objectcomputing.newsletter.live.views.SubscriberDetail
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
