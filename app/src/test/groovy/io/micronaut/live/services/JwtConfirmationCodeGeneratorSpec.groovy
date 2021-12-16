package io.micronaut.live.services

import com.nimbusds.jwt.JWT
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class JwtConfirmationCodeGeneratorSpec extends Specification {

    @Inject
    JwtConfirmationCodeGenerator jwtConfirmationCodeGenerator

    @Inject
    ConfirmationCodeVerifier confirmationCodeVerifier

    void "JwtConfirmationCodeGenerator generates a JWT with the user's email in the claims"() {
        when:
        Optional<String> jwtString = jwtConfirmationCodeGenerator.generate("tcook@apple.com")

        then:
        jwtString.isPresent()
        JWT jwt = JWTParser.parse(jwtString.get())
        jwt instanceof SignedJWT
        "tcook@apple.com" == ((SignedJWT) jwt).getJWTClaimsSet().getClaim("email")
        ((SignedJWT) jwt).getJWTClaimsSet().getExpirationTime().after(new Date())

        expect:
        confirmationCodeVerifier.verify(jwtString.get())
    }
}
