package io.micronaut.live.controllers

import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest(startApplication = false)
class EnvDeductionAddsTestEnvByDefaultWithAtMicronautTestSpec extends Specification {

    @Inject
    ApplicationContext ctx

    void "Micronaut environment deduction adds the test environment by default"() {
        //given:
        //ApplicationContext ctx = ApplicationContext.run()

        expect:
        ctx.environment.activeNames.contains(Environment.TEST)
        ctx.environment.activeNames.size() == 1

        //cleanup:
        //ctx.close()
    }
}
