package io.micronaut.live.conf

import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = 'appsecurity.api-keys', value = 'foobar')
@MicronautTest(startApplication = false)
class ApiKeyConfigurationPropertiesSpec extends Specification {

    @Inject
    ApiKeysConfiguration apiKeysConfiguration

    void "api keys are populated via configuration"() {
        expect:
        apiKeysConfiguration.apiKeys.isPresent()
        ['foobar'] == apiKeysConfiguration.apiKeys.get()
    }
}
