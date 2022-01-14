package io.micronaut.launch

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class AzureControllerSpec extends Specification implements TestPropertyProvider {

    @Inject
    @Client("/")
    HttpClient httpClient

    @AutoCleanup
    @Shared
    EmbeddedServer micronautLaunch = ApplicationContext.run(EmbeddedServer,
            Collections.singletonMap('spec.name', 'fakemicronautlaunch'))

    @Override
    Map<String, String> getProperties() {
        Collections.singletonMap('micronaut.http.services.micronautlaunch.url', "http://localhost:${micronautLaunch.port}".toString())
    }

    void "GET /azure exposes azure version"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        expect:
        new File('src/test/resources/versions.json').exists()

        when:
        String version = client.retrieve(HttpRequest.GET('/azure'))

        then:
        "1.4.2" == version
    }

    @Requires(property = "spec.name", value = "fakemicronautlaunch")
    @Controller
    static class MockMicronautLaunch {

        @PermitAll
        @Get("/versions")
        String index() {
            new File('src/test/resources/versions.json').text
        }
    }
}
