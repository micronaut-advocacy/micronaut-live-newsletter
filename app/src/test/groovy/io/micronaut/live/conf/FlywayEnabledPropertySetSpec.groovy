package io.micronaut.live.conf

import io.micronaut.context.ApplicationContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class FlywayEnabledPropertySetSpec extends Specification {
    @Inject
    ApplicationContext ctx

    void "flyway.datasources.default.enabled is set to true"() {
        when:
        Optional<Boolean> flywayEnabledOptional =  ctx.getProperty("flyway.datasources.default.enabled", Boolean)

        then:
        flywayEnabledOptional.isPresent()
        flywayEnabledOptional.get()
    }
}
