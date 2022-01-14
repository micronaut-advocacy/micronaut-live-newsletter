package io.micronaut.live.controllers

import io.micronaut.context.ApplicationContext
import spock.lang.PendingFeature
import spock.lang.Specification

class HelloControllerSpec extends Specification {

    @PendingFeature
    void "verify bean type of HelloController exists"() {
        given:
        ApplicationContext ctx = ApplicationContext.run()

        expect:
        ctx.containsBean(HelloController)

        cleanup:
        ctx.close()
    }
}
