package io.micronaut.live.security

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.live.services.SubscriberListService
import io.micronaut.live.views.Pagination
import io.micronaut.live.views.SubscriberListPage
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Property(name = "spec.name", value = "MockSubscriberListService")
@MicronautTest
class SherlockHolmesAuthenticationProviderSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "if no basic auth is provided 401 is returned"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        HttpRequest<?> request = HttpRequest.GET('/subscriber/list')

        when:
        client.exchange(request)

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.UNAUTHORIZED == e.status
    }

    void "if expected basic auth is provided 200 is returned"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        HttpRequest<?> request = HttpRequest.GET('/subscriber/list')
                .basicAuth("sherlock", "elementary")

        when:
        HttpResponse<?> response = client.exchange(request)

        then:
        noExceptionThrown()
        HttpStatus.OK == response.status()
    }

    @Requires(property = "spec.name", value = "MockSubscriberListService")
    @Singleton
    @Replaces(SubscriberListService.class)
    static class MockSubscriberListService implements SubscriberListService {
        @Override
        SubscriberListPage findAll(@NonNull @NotNull @Min(1L) Integer page) {
            return new SubscriberListPage("Subscriber List", [], Pagination.of(0, 10, "/subscriber", 1));
        }
    }


}
