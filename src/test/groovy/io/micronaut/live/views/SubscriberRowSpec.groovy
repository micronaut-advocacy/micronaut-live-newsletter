package io.micronaut.live.views

import io.micronaut.core.beans.BeanIntrospection
import spock.lang.Specification

class SubscriberRowSpec extends Specification {

    void "SubscriberRow is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriberRow)

        then:
        noExceptionThrown()
    }
}
