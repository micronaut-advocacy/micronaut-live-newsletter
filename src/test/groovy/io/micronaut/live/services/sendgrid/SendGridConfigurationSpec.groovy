package io.micronaut.live.services.sendgrid

import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SendGridConfigurationSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "by default there is not bean of type SendGridConfiguration"() {
        expect:
        !beanContext.containsBean(SendGridConfiguration)

    }
}
