package io.micronaut.live.controllers

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.Specification

//@MicronautTest(startApplication = false) == ApplicationContext.run()
//@MicronautTest == ApplicationContext.run(EmbeddedServer)
class SandwichControllerSpec extends Specification {

    void "You can start the application context with extra environments"() {

        given:
        ApplicationContext ctx = ApplicationContext.run("sandwich")

        expect:
        ctx.containsBean(SandwichController)
        ctx.environment.activeNames.contains("sandwich")
        ctx.environment.activeNames.contains("test")
        ctx.environment.activeNames.size() == 2

        cleanup:
        ctx.close()
    }


    void "you can run a server without at MicronautTest"() {
        given:
        EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer.class, "sandwich")
        ApplicationContext ctx = embeddedServer.applicationContext
        HttpClient httpClient = ctx.createBean(HttpClient, embeddedServer.URL)
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        String response = client.retrieve(HttpRequest.GET('/sandwich'))

        then:
        noExceptionThrown()
        'sandwich' == response

        when:

        //JUNIT 5 Executable e = () -> client.retrieve(HttpRequest.PATCH('/sandwich', [:]))
        //CommunicationException thrown = assertThrows(HttpClientResponseException.class, e);

        client.retrieve(HttpRequest.PATCH('/sandwich', [:]))

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.UNAUTHORIZED == e.status

        cleanup:
        ctx.close()
        embeddedServer.close()

    }
}
