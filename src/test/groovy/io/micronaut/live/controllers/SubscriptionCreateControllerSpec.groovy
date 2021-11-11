package io.micronaut.live.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class SubscriptionCreateControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "GET /subscribe/create renders an HTML Page"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET("/subscription/create")
                .accept(MediaType.TEXT_HTML)
        HttpResponse<?> response = client.exchange(request)

        then:
        noExceptionThrown()
        HttpStatus.OK == response.status()
        response.contentType.isPresent()
        MediaType.TEXT_HTML_TYPE == response.contentType.get()
    }
}
