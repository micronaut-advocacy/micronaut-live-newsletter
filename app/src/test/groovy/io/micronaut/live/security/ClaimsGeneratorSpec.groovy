package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.security.token.jwt.generator.claims.ClaimsGenerator
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class ClaimsGeneratorSpec extends Specification {

    @Inject
    BeanContext beanContext
    void "ClaimsGenerator is CustomClaimsGenerator"() {
        expect:
        beanContext.containsBean(ClaimsGenerator)

        and:
        beanContext.getBean(ClaimsGenerator) instanceof CustomClaimsGenerator
        beanContext.getBeansOfType(ClaimsGenerator).size() == 1
    }
}
