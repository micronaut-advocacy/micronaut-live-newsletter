package io.micronaut.views.turbo

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "spec.name", value = "TurboStreamSpec")
@MicronautTest
class TurboStreamSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "turbo stream append with target DOM ID and content"() {
        given:
        String template = "Content to append to container designated with the dom_id."
        String domId = "dom_id"
        TurboStreamAction action = TurboStreamAction.APPEND
        when:
        TurboStream turboStream = TurboStream.builder()
            .targetDomId(domId)
                .action(action)
                .template(template)
                .build()
        then:
        domId == turboStream.targetDomId.get()
        action == turboStream.action
        template == turboStream.template.get().get().get()

        when:
        turboStream = TurboStream.builder()
                .targetDomId(domId)
                .template(template)
                .append()
        then:
        domId == turboStream.targetDomId.get()
        action == turboStream.action
        template == turboStream.template.get().get().get()
    }

    void "targetDomId or targetCssQuerySelector is required"() {
        given:
        String domId = "dom_id"
        String cssSelector = ".elementsWithClass"
        when:
        TurboStream.builder().remove()

        then:
        IllegalArgumentException e = thrown()

        when:
        TurboStream.builder().targetCssQuerySelector(cssSelector).remove()

        then:
        noExceptionThrown()

        when:
        TurboStream.builder().targetDomId(domId).remove()

        then:
        noExceptionThrown()
    }


    void "action methods populate action and invoke build"() {
        given:
        String domId = "dom_id"

        expect:
        TurboStreamAction.REMOVE == TurboStream.builder().targetDomId(domId).remove().getAction()
        TurboStreamAction.APPEND == TurboStream.builder().targetDomId(domId).append().getAction()
        TurboStreamAction.PREPEND == TurboStream.builder().targetDomId(domId).prepend().getAction()
        TurboStreamAction.AFTER == TurboStream.builder().targetDomId(domId).after().getAction()
        TurboStreamAction.BEFORE == TurboStream.builder().targetDomId(domId).before().getAction()
        TurboStreamAction.UPDATE == TurboStream.builder().targetDomId(domId).update().getAction()
        TurboStreamAction.REPLACE == TurboStream.builder().targetDomId(domId).replace().getAction()
    }

    void "template is not required"() {
        given:
        String domId = "dom_id"
        when:
        TurboStream turboStream = TurboStream.builder()
                .targetDomId(domId)
                .remove()
        then:
        domId == turboStream.targetDomId.get()
        TurboStreamAction.REMOVE == turboStream.action
        !turboStream.template
    }

    void "TurboStream implements Writable"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        String html = client.retrieve(HttpRequest.GET("/turbo/append"))

        then:
        "<turbo-stream action=\"append\" target=\"dom_id\"><template>Content to append to container designated with the dom_id.</template></turbo-stream>" == html

        when:
        html = client.retrieve(HttpRequest.GET("/turbo/remove"))

        then:
        "<turbo-stream action=\"remove\" target=\"dom_id\"></turbo-stream>" == html
    }

    @Requires(property = "spec.name", value = "TurboStreamSpec")
    @Controller("/turbo")
    static class TurboStreamWriteableController {

        @Secured(SecurityRule.IS_ANONYMOUS)
        @Get("/append")
        HttpResponse<?> index() {
            HttpResponse.ok(TurboStream.builder()
                    .targetDomId("dom_id")
                    .template("Content to append to container designated with the dom_id.")
                    .append())
                    .contentType(TurboHttpHeaders.TURBO_STREAM)
        }

        @Secured(SecurityRule.IS_ANONYMOUS)
        @Get("/remove")
        HttpResponse<?> remove() {
            HttpResponse.ok(TurboStream.builder()
                    .targetDomId("dom_id")
                    .remove())
                    .contentType(TurboHttpHeaders.TURBO_STREAM)
        }
    }



}
