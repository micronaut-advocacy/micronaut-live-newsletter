package io.micronaut.live.controllers

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.live.services.ConfirmationCodeGenerator
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class UnsubscribeControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Shared
    BlockingHttpClient _client

    @Inject
    ConfirmationCodeGenerator confirmationCodeGenerator

    BlockingHttpClient getClient() {
        if (_client == null) {
            _client = httpClient.toBlocking()
        }
        _client
    }

    void "GET /unsubscribe token query value parameter is required"() {
        when: 'not token is supplied'
        String html = client.retrieve(createRequest(), String)

        then: 'app redirects to the 404 page'
        noExceptionThrown()
        html.contains('<h1>Not Found</h1>')
    }

    void "GET /unsubscribe if invalid token redirect the user to 404"() {
        when: 'not token is supplied'
        String html = client.retrieve(createRequest("foo"), String)

        then: 'app redirects to the 404 page'
        noExceptionThrown()
        html.contains('<h1>Not Found</h1>')
    }

    void "GET /unsubscribe if valid token renders a HTML Page telling the user unsubscription was successful"() {
        when: 'not token is supplied'
        Optional<String> tokenOptional = confirmationCodeGenerator.generate("tcook@apple.com")

        then:
        tokenOptional.isPresent()

        when:
        String html = client.retrieve(createRequest(tokenOptional.get()), String)

        then: 'app shows a happy path HTML'
        noExceptionThrown()
        html.contains('<h1>You are no longer subscribed</h1>')
    }

    private static HttpRequest<?> createRequest(@Nullable String token = null) {
        UriBuilder builder = UriBuilder.of('/unsubscribe')
        if (token) {
            builder = builder.queryParam("token", token)
        }
        HttpRequest.GET(builder.build())
                .accept(MediaType.TEXT_HTML)
    }

}
