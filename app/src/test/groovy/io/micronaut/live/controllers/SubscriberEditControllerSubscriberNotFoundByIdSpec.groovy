package io.micronaut.live.controllers

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder
import io.micronaut.live.Subscriber
import io.micronaut.live.services.SubscriberShowService
import io.micronaut.live.views.SubscriberDetail
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification

import javax.validation.constraints.NotBlank

@Property(name = "spec.name", value = "SubscriberEditControllerSubscriberNotFoundByIdSpec")
@Property(name = 'micronaut.security.filter.enabled', value = StringUtils.FALSE)
@MicronautTest
class SubscriberEditControllerSubscriberNotFoundByIdSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    void "subscriber detail page is secured"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        String id = '99'
        URI uri = UriBuilder.of('/subscriber').path(id).path('edit').build()
        HttpRequest<?> request = HttpRequest.GET(uri)
        client.exchange(request)

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.NOT_FOUND == e.status
        e.message.contains("Subscriber not found by id: 99")
    }

    @Requires(property = "spec.name", value = "SubscriberEditControllerSubscriberNotFoundByIdSpec")
    @Singleton
    @Replaces(SubscriberShowService.class)
    static class SubscriberShowServiceReplacement implements SubscriberShowService {

        @Override
        Optional<SubscriberDetail> findById(@NonNull @NotBlank String id) {
            return Optional.empty();
        }
    }
}
