package com.objectcomputing.newsletter.live

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder
import com.objectcomputing.newsletter.live.data.SubscriberDataRepository
import com.objectcomputing.newsletter.live.services.SubscriberSaveService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

@Property(name = 'micronaut.security.reject-not-found', value = StringUtils.FALSE)
@MicronautTest
class SecuredSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    @Unroll("#method #path is secured")
    void "verify routes which require authentication return 401"(HttpMethod method, String path) {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.create(method, path)
        client.exchange(request)

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.UNAUTHORIZED == e.status

        where:
        method           | path
        HttpMethod.POST  | '/api/v1/email'
        HttpMethod.GET   | '/subscriber/import'
        HttpMethod.POST  | '/subscriber/import'
        HttpMethod.GET   | '/subscriber/list'
        HttpMethod.GET   | '/newsletter/list'
        HttpMethod.GET   | '/newsletter/create'
        HttpMethod.GET   | '/newsletter/edit'
    }
}
