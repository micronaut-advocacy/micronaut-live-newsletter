package io.micronaut.live.testnative

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import spock.lang.Requires
import spock.lang.Specification

class ApiCountSpec extends Specification {

    @Requires({env['APP_URL']})
    void "GET /api/v1/subscriber/count for native image works"() {
        given:
        HttpClient httpClient = HttpClient.create(new URL(System.getenv('APP_URL') as String))
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET('/api/v1/subscriber/count')
        HttpResponse<?> response = client.exchange(request)

        then:
        noExceptionThrown()
        HttpStatus.OK == response.status()

        cleanup:
        httpClient.close()
    }
}

