package com.objectcomputing.newsletter.live.controllers

import com.objectcomputing.newsletter.live.Subscriber
import com.objectcomputing.newsletter.live.services.SubscriberSaveService
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification

import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Property(name = "spec.name", value = "SubscriberSaveControllerSpec")
@MicronautTest
class SubscriberSaveControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    void "if you submit the form you get a message telling you to confirm your subscription"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        when:
        String html = client.retrieve(HttpRequest.POST("/subscriber/save", [email: 'tcook@apple.com'])
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))

        then:
        noExceptionThrown()
        html.contains("Please, check your email and confirm your subscription")
    }

    @Requires(property = "spec.name", value = "SubscriberSaveControllerSpec")
    @Singleton
    static class SubscriberSaveServiceReplacement implements SubscriberSaveService {

        @Override
        boolean exists(@NonNull @NotBlank @Email String email) {
            return false
        }

        @Override
        Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber) {
            Optional.empty()
        }

        @Override
        void saveActiveSubscribers(@NonNull @NotNull Collection<Subscriber> subscribers) {

        }

        @Override
        Optional<String> saveActiveSubscriber(@NonNull @NotNull @Valid Subscriber subscriber) {
            Optional.empty()
        }
    }

}
