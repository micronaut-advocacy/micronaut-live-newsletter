package io.micronaut.live.api.v1

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.live.Subscriber
import io.micronaut.live.data.SubscriberService
import io.micronaut.live.model.Email
import io.micronaut.live.services.EmailSender
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.validation.Valid
import javax.validation.constraints.NotNull

@Property(name = "spec.name", value = "EmailControllerSpec")
@Property(name = "email.from", value = "tcook@apple.com")
@MicronautTest
class EmailControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    BeanContext beanContext

    void "POST /api/v1/email returns 202"() {
        given:
        String subject = "[Groovy Calamari] 179 - Groovy Calamari Returns"
        BlockingHttpClient client = httpClient.toBlocking()
        EmailRequest body = new EmailRequest(subject)

        when:
        HttpResponse<?> response = client.exchange(HttpRequest.POST("/api/v1/email", body))

        then:
        noExceptionThrown()
        202 == response.status().code

        and:
        new PollingConditions().eventually {
            assert beanContext.getBean(EmailSenderReplacement).emails.size() == 2
            assert beanContext.getBean(EmailSenderReplacement).emails.any {email ->
                email.subject == subject && email.to == 'tcue@apple.com' && email.from == 'tcook@apple.com'
            }
            assert beanContext.getBean(EmailSenderReplacement).emails.any {email ->
                email.subject == subject && email.to == 'lmaestri@apple.com' && email.from == 'tcook@apple.com'
            }
        }
    }

    @Requires(property = "spec.name", value = "EmailControllerSpec")
    @Singleton
    @Replaces(SubscriberService.class)
    static class SubscriberServiceReplacement implements SubscriberService {

        @Override
        Integer countSubscribers() {
            2
        }

        @Override
        List<Subscriber> findAll() {
            [new Subscriber("tcue@apple.com"),
             new Subscriber("lmaestri@apple.com")]
        }
    }

    @Requires(property = "spec.name", value = "EmailControllerSpec")
    @Singleton
    @Replaces(EmailSender.class)
    static class EmailSenderReplacement implements EmailSender {
        List<Email> emails = []
        @Override
        void sendEmail(@NonNull @NotNull @Valid Email email) {
            emails.add(email)
        }
    }

    void "POST /api/v1/email with invalid payload returns 400"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        EmailRequest body = new EmailRequest("")

        when:
        client.exchange(HttpRequest.POST("/api/v1/email", body))

        then:
        HttpClientResponseException e = thrown()
        400 == e.status.code
    }
}
