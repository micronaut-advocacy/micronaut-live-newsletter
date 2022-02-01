package com.objectcomputing.newsletter.live.controllers

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder
import com.objectcomputing.newsletter.live.Subscriber
import com.objectcomputing.newsletter.live.data.PostgresTestPropertyProvider
import com.objectcomputing.newsletter.live.data.SubscriberDataRepository
import com.objectcomputing.newsletter.live.services.SubscriberSaveService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = 'micronaut.security.reject-not-found', value = StringUtils.FALSE)
@MicronautTest
class SubscriberShowControllerSecuredSpec extends Specification implements PostgresTestPropertyProvider {
    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberSaveService subscriberSaveService

    @Inject
    SubscriberDataRepository subscriberDataRepository

    void "subscriber detail page is secured"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        Optional<String> idOptional = subscriberSaveService.saveActiveSubscriber(new Subscriber("tcook@apple.com"))

        then:
        idOptional.isPresent()

        when:
        String id = idOptional.get()
        HttpRequest<?> request = HttpRequest.GET(UriBuilder.of('/subscriber').path(id).build())
        client.exchange(request)

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.UNAUTHORIZED == e.status

        cleanup:
        subscriberDataRepository.deleteById(id)
    }
}
