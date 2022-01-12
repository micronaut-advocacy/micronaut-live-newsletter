package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.security.token.generator.RefreshTokenGenerator
import io.micronaut.security.token.validator.RefreshTokenValidator
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class RefreshTokenValidatorSpec extends Specification {
    @Inject
    BeanContext beanContext
    void "bean of type RefreshTokenValidator exists"() {
        expect:
        beanContext.containsBean(RefreshTokenValidator)
    }
}
