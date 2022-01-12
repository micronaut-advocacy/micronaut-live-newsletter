package io.micronaut.live.security

import io.micronaut.context.BeanContext
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.ldap.LdapAuthenticationProvider
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class AuthenticationProviderSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "Multiple AuthenticationProvider exists"() {
        expect:
        beanContext.containsBean(AuthenticationProvider)
        when:
        List<AuthenticationProvider> authenticationProviderList = beanContext.getBeansOfType(AuthenticationProvider)

        then:
        authenticationProviderList.size() == 2

        and:
        authenticationProviderList.stream().anyMatch(authProvider -> authProvider instanceof LdapAuthenticationProviderReplacement)
        authenticationProviderList.stream().anyMatch(authProvider -> authProvider instanceof LdapAuthenticationProvider)
        authenticationProviderList.stream().anyMatch(authProvider -> authProvider instanceof SherlockHolmesAuthenticationProvider)
        //authenticationProviderList.stream().anyMatch(authProvider -> authProvider instanceof AllowedUsersAuthenticationProvider)
    }

}
