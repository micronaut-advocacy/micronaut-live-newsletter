package io.micronaut.live.controllers

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "micronaut.http.client.follow-redirects", value = StringUtils.FALSE)
@MicronautTest
class HomeControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "GET / redirects to /subscription/create"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        when:
        HttpResponse<?> response = client.exchange(HttpRequest.GET("/"))

        then:
        noExceptionThrown()
        303 == response.status().code
    }
}
