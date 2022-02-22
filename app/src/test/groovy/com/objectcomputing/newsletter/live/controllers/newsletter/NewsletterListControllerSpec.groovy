package com.objectcomputing.newsletter.live.controllers.newsletter

import com.objectcomputing.newsletter.live.data.PostgresTestPropertyProvider
import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
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

@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@MicronautTest
class NewsletterListControllerSpec extends Specification implements PostgresTestPropertyProvider {
    @Inject
    @Client("/")
    HttpClient httpClient

    BlockingHttpClient getClient() {
        httpClient.toBlocking()
    }

    void "GET /newsletter renders an HTML listing"() {
        given:
        HttpRequest<?> request = HttpRequest.GET('/newsletter/list')
                .accept(MediaType.TEXT_HTML)

        when:
        HttpResponse<String> response = client.exchange(request)

        then:
        noExceptionThrown()
        HttpStatus.OK == response.status()
    }
}
