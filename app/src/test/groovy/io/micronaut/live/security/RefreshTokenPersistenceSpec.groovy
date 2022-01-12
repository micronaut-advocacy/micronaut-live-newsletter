package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class RefreshTokenPersistenceSpec extends Specification {
    @Inject
    BeanContext beanContext
    void "bean of type RefreshTokenPersistence exists"() {
        expect:
        beanContext.containsBean(RefreshTokenPersistence)
    }
}
