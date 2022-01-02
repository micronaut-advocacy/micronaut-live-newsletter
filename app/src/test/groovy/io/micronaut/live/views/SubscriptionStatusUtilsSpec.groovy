package io.micronaut.live.views

import io.micronaut.live.model.SubscriptionStatus
import spock.lang.Specification
import spock.lang.Unroll

class SubscriptionStatusUtilsSpec extends Specification {

    @Unroll
    void "Bootstrap alert css class for a SubscriptionStatus"(SubscriptionStatus status, String cssClass) {
        expect:
        cssClass == SubscriptionStatusUtils.alertClass(status)

        where:
        status                      || cssClass
        SubscriptionStatus.ACTIVE   || 'alert-success'
        SubscriptionStatus.PENDING  || 'alert-warning'
        SubscriptionStatus.CANCELED || 'alert-danger'
    }
}
