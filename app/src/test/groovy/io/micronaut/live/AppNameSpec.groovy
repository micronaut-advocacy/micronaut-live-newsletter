package io.micronaut.live

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false,
propertySources = "classpath:foo.properties")
class AppNameSpec extends Specification {

    @Inject
    ApplicationContext ctx

    void "application name is newsletter"() {
        when:
        Optional<String> appNameOptional =  ctx.getProperty("micronaut.application.name", String)

        then:
        appNameOptional.isPresent()
        "foo" == appNameOptional.get()
    }

}
