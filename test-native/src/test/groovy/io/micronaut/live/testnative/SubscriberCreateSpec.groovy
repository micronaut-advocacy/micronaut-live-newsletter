package io.micronaut.live.testnative

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import spock.lang.Requires
import spock.lang.Specification

class SubscriberCreateSpec extends Specification {

    @Requires({env['APP_URL']})
    void "GET /subscriber/create renders an HTML Page"() {
        given:
        HttpClient httpClient = HttpClient.create(new URL(System.getenv('APP_URL') as String))
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET('/subscriber/create')
        String html = client.retrieve(request)

        then:
        noExceptionThrown()
        html.contains('<button type="submit" class="btn btn-primary">Subscribe</button>')

        cleanup:
        httpClient.close()
    }
}
