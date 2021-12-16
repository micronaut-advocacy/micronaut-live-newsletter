package io.micronaut.live.testnative

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.DefaultHttpClientConfiguration
import io.micronaut.http.client.HttpClient
import spock.lang.Requires
import spock.lang.Specification

class HomeSpec extends Specification {

    @Requires({env['APP_URL']})
    void "GET / redirects to /subscriber/create"() {
        given:
        DefaultHttpClientConfiguration conf = new DefaultHttpClientConfiguration()
        conf.setFollowRedirects(false)
        HttpClient httpClient = HttpClient.create(new URL(System.getenv('APP_URL') as String), conf)
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET('/')
        HttpResponse<?> response = client.exchange(request)

        then:
        noExceptionThrown()
        HttpStatus.SEE_OTHER == response.status()
        response.header(HttpHeaders.LOCATION)
        "/subscriber/create" == response.header(HttpHeaders.LOCATION)

        cleanup:
        httpClient.close()
    }
}
