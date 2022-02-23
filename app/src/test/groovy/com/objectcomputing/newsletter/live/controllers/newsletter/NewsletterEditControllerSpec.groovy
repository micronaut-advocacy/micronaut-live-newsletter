package com.objectcomputing.newsletter.live.controllers.newsletter

import com.objectcomputing.newsletter.live.newsletter.services.NewsletterEditService
import com.objectcomputing.newsletter.live.newsletter.services.NewsletterUpdateForm
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification
import javax.validation.constraints.NotBlank

@Property(name = "spec.name", value = "NewsletterEditControllerSpec")
@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@MicronautTest
class NewsletterEditControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    BlockingHttpClient getClient() {
        httpClient.toBlocking()
    }

    void "GET /newsletter/edit renders an HTML listing"() {
        given:
        HttpRequest<?> request = HttpRequest.GET('/newsletter/xxx/edit')
                .accept(MediaType.TEXT_HTML)

        when:
        HttpResponse<String> response = client.exchange(request)

        then:
        noExceptionThrown()
        HttpStatus.OK == response.status()
    }

    @Requires(property = "spec.name", value = "NewsletterEditControllerSpec")
    @Replaces(NewsletterEditService)
    @Singleton
    static class MockNewsletterEditService implements NewsletterEditService {
        @Override
        @NonNull
        Optional<NewsletterUpdateForm> edit(@NonNull @NotBlank String id) {
            Optional.of(new NewsletterUpdateForm("xxx", "Groovy Calamari"));
        }
    }
}
