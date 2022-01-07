package io.micronaut.live.views

import io.micronaut.core.beans.BeanIntrospection
import spock.lang.Specification

class SubscriberDetailPageSpec extends Specification {
    void "io.micronaut.live.views.SubscriberDetailPage is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriberDetailPage)

        then:
        noExceptionThrown()
    }
}
