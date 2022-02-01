package com.objectcomputing.newsletter.live.views

import io.micronaut.core.beans.BeanIntrospection
import com.objectcomputing.newsletter.live.model.SubscriptionStatus
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
        cssClass == new SubscriberRow("123", "tcook@apple.com", status).statusAlertClass()

        where:
        status                      || cssClass
        SubscriptionStatus.ACTIVE   || 'alert-success'
        SubscriptionStatus.PENDING  || 'alert-warning'
        SubscriptionStatus.CANCELED || 'alert-danger'
    }
}
