package io.micronaut.live.api.v1

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.util.CollectionUtils
import io.micronaut.core.util.StringUtils
import io.micronaut.email.Contact
import io.micronaut.email.Email
import io.micronaut.email.EmailException
import io.micronaut.email.EmailSender
import io.micronaut.email.TransactionalEmailSender
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.live.Subscriber
import io.micronaut.live.data.SubscriberService
import io.micronaut.live.services.DevEmailSender
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.validation.Valid
import javax.validation.constraints.NotNull
import java.util.function.Consumer

@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@Property(name = "spec.name", value = "EmailControllerSpec")
@Property(name = "micronaut.email.from.name", value = "Tim Cook")
@Property(name = "micronaut.email.from.email", value = "tcook@apple.com")
@MicronautTest
class EmailSendControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    BeanContext beanContext

    void "POST /api/v1/email returns 202"() {
        given:
        String subject = "[Groovy Calamari] 179 - Groovy Calamari Returns"
        BlockingHttpClient client = httpClient.toBlocking()
        EmailRequest body = new EmailRequest(subject, "blbabalba", "blbabalba")

        when:
        HttpResponse<?> response = client.exchange(HttpRequest.POST("/api/v1/email", body))

        then:
        noExceptionThrown()
        202 == response.status().code

        and:
        new PollingConditions().eventually {
            assert beanContext.getBean(EmailSenderReplacement).emails.size() == 2
        }
        beanContext.getBean(EmailSenderReplacement).emails.any {email ->
            email.subject == subject && CollectionUtils.isNotEmpty(email.to) && email.to[0].email == 'tcue@apple.com' && email.from  && email.from.email == 'tcook@apple.com'
        }
        beanContext.getBean(EmailSenderReplacement).emails.any {email ->
            email.subject == subject && CollectionUtils.isNotEmpty(email.to) && email.to[0].email == 'lmaestri@apple.com' && email.from && email.from.email == 'tcook@apple.com'
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
    @Replaces(EmailSender.class)
    @Singleton
    @Named("mock")
    static class EmailSenderReplacement implements TransactionalEmailSender<Email, Void> {
        List<Email> emails = []

        @Override
        String getName() {
            return "mock"
        }

        @Override
        @NonNull
        public Void send(@NonNull @NotNull @Valid Email email,
                         @NonNull @NotNull Consumer<Email> emailRequest) throws EmailException {
            emails << email
            return null
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
