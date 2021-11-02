package io.micronaut.live.api.v1

import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.live.data.SubscriberDataRepository
import io.micronaut.live.data.SubscriberEntity
import io.micronaut.live.services.IdGenerator
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(transactional = false)
class SubscriberCountControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberDataRepository subscriberDataRepository

    @Inject
    IdGenerator idGenerator

    void "GET /api/v1/subscriber/count returns the number of confirmed subscribers"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET('/api/v1/subscriber/count')
                .accept(MediaType.TEXT_PLAIN)
        Integer result = client.retrieve(request, Integer)

        then:
        noExceptionThrown()
        0 == result

        when:
        String id = idGenerator.generate().get()
        subscriberDataRepository.save(new SubscriberEntity(id,
                "tcook@apple.com",
                "Tim Cook",
                true))
        result = client.retrieve(request, Integer)

        then:
        noExceptionThrown()
        1 == result

        cleanup:
        subscriberDataRepository.deleteById(id)

    }
}
