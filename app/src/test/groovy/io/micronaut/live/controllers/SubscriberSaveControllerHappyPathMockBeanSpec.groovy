package io.micronaut.live.controllers

import io.micronaut.context.BeanContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.live.data.SubscriberSaveServiceImpl
import io.micronaut.live.services.SubscriberSaveService
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class SubscriberSaveControllerHappyPathMockBeanSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberSaveService subscriberSaveService

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

        //and:
        //1 * subscriberSaveService.save(_, _)
    }

    @MockBean(SubscriberSaveService)
    SubscriberSaveService subscriberSaveService() {
        Mock(SubscriberSaveService)
    }
}
