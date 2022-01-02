package io.micronaut.live.views

import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.live.model.SubscriptionStatus
import spock.lang.Specification
import spock.lang.Unroll

class SubscriberRowSpec extends Specification {

    void "SubscriberRow is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(SubscriberRow)

        then:
        noExceptionThrown()
    }

    @Unroll
    void "Bootstrap alert css class for a SubscriptionStatus"(SubscriptionStatus status, String cssClass) {
        expect:
        cssClass == new SubscriberRow("tcook@apple.com", status).statusAlertClass()

        where:
        status                      || cssClass
        SubscriptionStatus.ACTIVE   || 'alert-success'
        SubscriptionStatus.PENDING  || 'alert-warning'
        SubscriptionStatus.CANCELED || 'alert-danger'
    }
}
