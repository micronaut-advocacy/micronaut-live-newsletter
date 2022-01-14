package io.micronaut.live.controllers

import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import spock.lang.Specification

class EnvDeductionAddsTestEnvByDefaultSpec extends Specification {

    void "Micronaut environment deduction adds the test environment by default"() {
        given:
        ApplicationContext ctx = ApplicationContext.run()

        expect:
        ctx.environment.activeNames.contains(Environment.TEST)
        ctx.environment.activeNames.size() == 1

        cleanup:
        ctx.close()
    }
}
