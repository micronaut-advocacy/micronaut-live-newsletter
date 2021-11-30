package io.micronaut.live.api.v1

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.live.Subscriber
import io.micronaut.live.services.SubscriberSaveService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Valid
import javax.validation.constraints.NotNull

@Property(name = "spec.name", value = "SubscriberSaveControllerSpec")
@MicronautTest
class SubscriberSaveControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberSaveService subscriberSaveService

    void "missing subscriber returns 400"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.exchange(HttpRequest.POST('/api/v1/subscriber', '{}'))

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.BAD_REQUEST == e.status
        e.response.contentType.isPresent()
        'application/problem+json' == e.response.contentType.get().toString()
    }

    void "for happy path a POST request to /api/v1/subscriber delegates to SubscriberSaveService::save"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.exchange(HttpRequest.POST('/api/v1/subscriber', [email: 'tcook@apple.com']))

        then:
        noExceptionThrown()
        subscriberSaveService instanceof SubscriberSaveServiceReplacement
        1 == ((SubscriberSaveServiceReplacement) subscriberSaveService).invocations
    }

    @Unroll("POST /api/v1/subscriber with invalid email #email returns 400")
    void "subscriber with invalid email returns 400"(String email) {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.exchange(HttpRequest.POST('/api/v1/subscriber', Collections.singletonMap("email", email)))

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.BAD_REQUEST == e.status
        e.response.contentType.isPresent()
        'application/problem+json' == e.response.contentType.get().toString()

        where:
        email << [null, '', 'tcook']
    }

    @Requires(property = "spec.name", value = "SubscriberSaveControllerSpec")
    @Replaces(SubscriberSaveService)
    @Singleton
    static class SubscriberSaveServiceReplacement implements SubscriberSaveService {

        int invocations

        @Override
        @NonNull
        Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber) {
            invocations++
            Optional.empty()
        }

        @Override
        void saveActiveSubscribers(@NonNull @NotNull Collection<Subscriber> subscribers) {

        }
    }
}
