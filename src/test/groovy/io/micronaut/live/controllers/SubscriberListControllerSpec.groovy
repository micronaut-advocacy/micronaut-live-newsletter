package io.micronaut.live.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class SubscriberListControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "GET /subscriber/list displays the subscribers HTML page"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET('/subscriber/list')
                .accept(MediaType.TEXT_HTML)
        String html = client.retrieve(request)

        then:
        noExceptionThrown()
        html
        html.contains('<h1>Subscribers</h1>')
    }
}
