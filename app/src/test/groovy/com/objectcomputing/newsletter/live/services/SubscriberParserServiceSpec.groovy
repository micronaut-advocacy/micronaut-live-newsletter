package com.objectcomputing.newsletter.live.services

import io.micronaut.context.BeanContext
import com.objectcomputing.newsletter.live.Subscriber
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberParserServiceSpec extends Specification {

    @Inject
    BeanContext beanContext
    void "ImportService can parse subscribers from CSV file"() {
        given:
        File f = new File('src/test/resources/50-contacts.csv')

        expect:
        beanContext.containsBean(SubscriberParserService)
        f.exists()
        when:
        SubscriberParserService importService = beanContext.getBean(SubscriberParserService)
        Optional<List<Subscriber>> subscriberListOptional = importService.parseSubscribers(f.newInputStream())

        then:
        noExceptionThrown()
        subscriberListOptional.isPresent()
        subscriberListOptional.get().size() == 50
    }
}
