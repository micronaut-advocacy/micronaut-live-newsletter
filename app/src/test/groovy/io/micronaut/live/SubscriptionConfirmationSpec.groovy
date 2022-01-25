package io.micronaut.live

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.email.BodyType
import io.micronaut.email.Email
import io.micronaut.email.EmailException
import io.micronaut.email.EmailSender
import io.micronaut.email.TransactionalEmailSender
import io.micronaut.email.configuration.FromConfiguration
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.live.data.PostgresTestPropertyProvider
import io.micronaut.live.data.SubscriberService
import io.micronaut.live.data.SubscriberDataRepository
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.validation.Valid
import javax.validation.constraints.NotNull
import java.util.function.Consumer

@Property(name = "micronaut.email.from.name", value = "Tim Cook")
@Property(name = "micronaut.email.from.email", value = "tcook@apple.com")
@Property(name = "spec.name", value = "SubscriptionConfirmationSpec")
@MicronautTest
class SubscriptionConfirmationSpec extends Specification implements PostgresTestPropertyProvider {
    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberDataRepository subscriberDataRepository

    @Inject
    SubscriberService subscriberCountService

    @Inject
    BeanContext beanContext

    void "happy path for user subscription and confirmation"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        expect:
        beanContext.containsBean(FromConfiguration)

        when:
        client.exchange(HttpRequest.POST('/api/v1/subscriber', [email: 'tcook@apple.com']))

        then:
        noExceptionThrown()
        subscriberDataRepository.count() == old(subscriberDataRepository.count()) + 1

        new PollingConditions().eventually {
            assert emailsSent() == 1
        }
        subscriberCountService.countSubscribers() == 0

        when:
        Optional<String> confirmationEmailOptional = latestConfirmationLink()

        then:
        confirmationEmailOptional.isPresent()

        //TODO confirm the subscription and verify countSubscribers() returns 1

        cleanup:
        latestConfirmationLink().ifPresent(confirmationEmail -> println(confirmationEmail))
        beanContext.getBean(EmailSenderCollector).emails.clear()
        subscriberDataRepository.deleteAll()
    }

    Optional<String> latestConfirmationLink() {
        Optional<Email> emailOptional = lastEmail()
        if (emailOptional.isPresent()) {
            Email email = emailOptional.get()
            if (email.getBody() != null && email.getBody().get(BodyType.TEXT).isPresent()) {
                String[] arr = email.getBody().get(BodyType.TEXT).get().split("\n");
                return (arr as List<String>)
                        .stream()
                        .filter(l -> l.startsWith('http'))
                        .findFirst()
            }
        }
        Optional.empty()
    }

    Optional<Email> lastEmail() {
        EmailSenderCollector emailSenderCollector = beanContext.getBean(EmailSenderCollector)
        try {
            Email email = emailSenderCollector.emails.last()
            return Optional.of(email)
        } catch(NoSuchElementException e) {
            return Optional.empty()
        }
    }


    private int emailsSent() {
        EmailSenderCollector emailSenderCollector = beanContext.getBean(EmailSenderCollector)
        emailSenderCollector.emails.size()
    }

    @Requires(property = "spec.name", value = "SubscriptionConfirmationSpec")
    @Replaces(EmailSender.class)
    @Singleton
    @Named("mock")
    static class EmailSenderCollector implements TransactionalEmailSender<Email, Void> {
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
}
