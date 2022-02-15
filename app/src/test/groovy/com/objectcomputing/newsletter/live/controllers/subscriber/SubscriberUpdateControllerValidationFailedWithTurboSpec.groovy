package com.objectcomputing.newsletter.live.controllers.subscriber

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.views.turbo.TurboHttpHeaders
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@MicronautTest
class SubscriberUpdateControllerValidationFailedWithTurboSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    void "submitting an invalid form with a turbo request returns a turbo stream"() {
        given:
        String id = 'xxx'
        String name = 'Tim'
        String email = 'tcook'
        HttpRequest<?> request = HttpRequest.POST('/subscriber/update', [id: id, name: name, email: email])
                .contentType(MediaType.APPLICATION_FORM_URLENCODED +";charset=UTF-8")
                .accept(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML, TurboHttpHeaders.TURBO_STREAM)
                .header(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.2 Safari/605.1.15")
                .header(TurboHttpHeaders.TURBO_FRAME, "subscriber-container")
        BlockingHttpClient client = httpClient.toBlocking()
        when:
        HttpResponse<String> response = client.exchange(request, String)

        then:
        HttpStatus.OK == response.status()

        when:
        String html = response.body()
        String expected = """\
<turbo-stream action="update" target="subscriber-container"><template><div>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item active"
    aria-current="page">
    <a href="/subscriber/list">List</a>
    
</li>
            <li class="breadcrumb-item"><a href="/subscriber/${id}">Subscriber Detail</a></li>
            <li class="breadcrumb-item active"
    aria-current="page">
    
    <span>Edit Subscriber</span>
</li>
        </ol>
    </nav>
    <form method="post" action="/subscriber/update">
        <input type="hidden" name="id" value="${id}"/>
        <div class="mb-3">
    <label for="subscriber-name" class="form-label">Name</label>
    <input type="text"
           name="name"
           class="form-control"
           id="subscriber-name"
           value="${name}"/>
    
    
</div>
        <div class="mb-3">
    <label for="subscriber-email" class="form-label">Email address</label>
    <input type="email"
           name="email"
           class="form-control is-invalid"
           id="subscriber-email"
           value="${email}"/>
    
    <div id="emailValidationFeedback" class="invalid-feedback">must be a well-formed email address</div>
    
    
</div>
        <button type="submit"
        class="btn btn-primary">
    Update Subscriber
</button>
    </form>
</div></template></turbo-stream>""".toString()

        then:
        html == expected
    }
}
