package io.micronaut.live

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.live.data.SubscriberCountService
import io.micronaut.live.data.SubscriberDataRepository
import io.micronaut.live.model.Email
import io.micronaut.live.services.EmailSender
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification

import javax.validation.Valid
import javax.validation.constraints.NotNull

@Property(name = "spec.name", value = "SubscriptionConfirmationSpec")
@MicronautTest
class SubscriptionConfirmationSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberDataRepository subscriberDataRepository

    @Inject
    SubscriberCountService subscriberCountService

    @Inject
    BeanContext beanContext

    void "happy path for user subscription and confirmation"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.exchange(HttpRequest.POST('/api/v1/subscriber', [email: 'tcook@apple.com']))

        then:
        noExceptionThrown()
        subscriberDataRepository.count() == old(subscriberDataRepository.count()) + 1
        emailsSent() == 1
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
            String[] arr = email.getText().split("\n");
            return (arr as List<String>)
                    .stream()
                    .filter(l -> l.startsWith('http'))
                    .findFirst()
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
    static class EmailSenderCollector implements EmailSender {
        List<Email> emails = []

        @Override
        void sendEmail(@NonNull @NotNull @Valid Email email) {
            emails.add(email)
        }
    }
}
