package io.micronaut.live.controllers

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@MicronautTest(transactional = false)
class SubscriberImportCreateControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    void "GET /subscriber/import renders a HTML page with a form"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        String html = client.retrieve(HttpRequest.GET('/subscriber/import')
                .accept(MediaType.TEXT_HTML))

        then:
        noExceptionThrown()
        html.contains("type=\"file\" id=\"file\" name=\"file\"")
    }
}
