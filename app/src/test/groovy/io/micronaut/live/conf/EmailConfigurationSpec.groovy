package io.micronaut.live.conf

import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class EmailConfigurationSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "by default there is not bean of type EmailConfiguration"() {
        expect:
        !beanContext.containsBean(EmailConfiguration)

    }
}
