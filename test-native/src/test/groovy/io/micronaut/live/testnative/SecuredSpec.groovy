package io.micronaut.live.testnative

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import spock.lang.Requires
import spock.lang.Specification
import spock.lang.Unroll

class SecuredSpec extends Specification {

    @Requires({env['APP_URL']})
    @Unroll("#method #path is secured")
    void "verify routes which require authentication return 401"(HttpMethod method, String path) {
        given:
        HttpClient httpClient = HttpClient.create(new URL(System.getenv('APP_URL') as String))
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.create(method, path)
        client.exchange(request)

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.UNAUTHORIZED == e.status

        where:
        method           | path
        HttpMethod.POST  | '/api/v1/email'
        HttpMethod.GET   | '/subscriber/import'
        HttpMethod.POST  | '/subscriber/import'
        HttpMethod.GET   | '/subscriber/list'
    }

}
