package io.micronaut.launch

import io.micronaut.context.BeanContext
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import reactor.core.publisher.Mono
import spock.lang.Specification

@MicronautTest(startApplication = false)
class VersionsClientSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "there is a bean of type VersionClient"() {
        expect:
        beanContext.containsBean(VersionsClient)

        when:
        VersionsClient versionsClient = beanContext.getBean(VersionsClient)
        VersionsResponse versionsResponse = Mono.from(versionsClient.fetch()).block()

        then:
        versionsResponse

        cleanup:
        println versionsResponse.versions
    }

}
