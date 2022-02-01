package com.objectcomputing.newsletter.live.api.v1

import io.micronaut.http.HttpMethod
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
import io.micronaut.http.HttpHeaders

@MicronautTest(transactional = false)
class SubscriberCountControllerCorsSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "GET /api/v1/subscriber/count returns CORS headers"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        String path = '/api/v1/subscriber/count'
        String origin = 'groovycalamari.com'

        when:
        HttpResponse<?> response = client.exchange(HttpRequest.OPTIONS(path)
                .header(HttpHeaders.ORIGIN, origin)
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET)
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "${HttpHeaders.CONTENT_TYPE},${HttpHeaders.ACCEPT}"))

        then:
        noExceptionThrown()

        when:
        Set<String> optionsHeaderNames = response.headers.names()

        then:
        response.status == HttpStatus.OK
        response.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN) == origin
        response.header(HttpHeaders.VARY) == HttpHeaders.ORIGIN
        optionsHeaderNames.contains(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS)
        optionsHeaderNames.contains(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)
    }
}
