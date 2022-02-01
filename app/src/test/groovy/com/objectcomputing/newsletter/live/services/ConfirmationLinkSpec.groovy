package com.objectcomputing.newsletter.live.services

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class ConfirmationLinkSpec extends Specification {

    @Inject
    ConfirmationEmailComposer confirmationEmailComposer

    void "confirmation link is /subscriber/confirm"() {
        expect:
        confirmationEmailComposer.composeText('tcook@apple.com').contains('/subscriber/confirm')
    }
}
