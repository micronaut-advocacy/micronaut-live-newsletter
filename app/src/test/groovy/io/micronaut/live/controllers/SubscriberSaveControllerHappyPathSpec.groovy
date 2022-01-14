package io.micronaut.live.controllers

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.live.Subscriber
import io.micronaut.live.services.SubscriberSaveService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification

import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Property(name = "spec.name", value = "SubscriberSaveControllerHappyPathSpec")
@MicronautTest
class SubscriberSaveControllerHappyPathSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "POST /subscriber happy path renders and alter page telling the user to check his email"() {
        given:
        Map<String, String> form = [email: 'tcook@apple.com']
        HttpRequest<?> request = HttpRequest.POST('/subscriber/save', form)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpResponse<String> response = client.exchange(request, String)

        then:
        HttpStatus.OK == response.status()
        response.contentType.isPresent()
        MediaType.TEXT_HTML_TYPE == response.contentType.get()

        when:
        String html = response.getBody()

        then:
        html
        html.contains("Please, check your email and confirm your subscription")
    }

    @Requires(property = "spec.name", value = "SubscriberSaveControllerHappyPathSpec")
    @Replaces(SubscriberSaveService.class)
    @Singleton
    static class SubscriberSaveServiceReplacement implements SubscriberSaveService {

        public int invocations = 0

        @Override
        boolean exists(@NonNull @NotBlank @Email String email) {
            return false
        }

        @Override
        @NonNull
        Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber) {
            invocations++
            Optional.of(UUID.randomUUID().toString())
        }

        @Override
        void saveActiveSubscribers(@NonNull @NotNull Collection<Subscriber> subscribers) {

        }

        @Override
        @NonNull
        Optional<String> saveActiveSubscriber(@NonNull @NotNull @Valid Subscriber subscriber) {
            Optional.empty()
        }
    }
}
