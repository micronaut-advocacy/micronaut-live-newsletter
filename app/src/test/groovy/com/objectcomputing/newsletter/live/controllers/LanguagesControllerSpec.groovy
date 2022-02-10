package com.objectcomputing.newsletter.live.controllers

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.server.util.locale.HttpLocaleResolutionConfiguration
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

@Property(name = "micronaut.http.client.follow-redirects", value = StringUtils.FALSE)
@MicronautTest
class LanguagesControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    HttpLocaleResolutionConfiguration httpLocaleResolutionConfiguration

    @Unroll("GET /lang/#locale sets a cookie with name lang and value #locale")
    void "LanguagesController allows to set a cookie to change the value used for Locale resolution"(Locale locale) {
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        HttpRequest<?> request = HttpRequest.GET(UriBuilder.of('/lang').path(locale.toString()).build())

        expect:
        httpLocaleResolutionConfiguration.getCookieName().isPresent()

        when:
        HttpResponse<?> response = client.exchange(request)

        then:
        HttpStatus.SEE_OTHER == response.status()
        "/" == response.getHeaders().get(HttpHeaders.LOCATION)

        when:
        String cookie = response.getHeaders().get(HttpHeaders.SET_COOKIE)

        then:
        cookie.contains("Path=/")
        cookie.contains("Max-Age")
        cookie.contains("${httpLocaleResolutionConfiguration.getCookieName().get()}=${locale}")

        where:
        locale << [Locale.forLanguageTag("es"), Locale.ENGLISH]
    }

}
