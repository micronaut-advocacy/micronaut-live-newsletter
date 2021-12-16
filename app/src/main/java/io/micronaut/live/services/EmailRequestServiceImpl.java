package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.live.Subscriber;
import io.micronaut.live.api.v1.EmailRequest;
import io.micronaut.live.conf.EmailConfiguration;
import io.micronaut.live.data.SubscriberService;
import io.micronaut.live.model.Email;
import jakarta.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class EmailRequestServiceImpl implements EmailRequestService {

    public static final String PLACEHOLDER_UNSUBSCRIBE = "@unsubscribe@";
    private final SubscriberService subscriberService;
    private final EmailConfiguration emailConfiguration;
    private final EmailSender emailSender;
    private final HttpHostResolver httpHostResolver;
    private final ConfirmationCodeGenerator confirmationCodeGenerator;

    public EmailRequestServiceImpl(SubscriberService subscriberService,
                                   EmailConfiguration emailConfiguration,
                                   EmailSender emailSender,
                                   HttpHostResolver httpHostResolver,
                                   ConfirmationCodeGenerator confirmationCodeGenerator) {
        this.subscriberService = subscriberService;
        this.emailConfiguration = emailConfiguration;
        this.emailSender = emailSender;
        this.httpHostResolver = httpHostResolver;
        this.confirmationCodeGenerator = confirmationCodeGenerator;
    }

    @Override
    public void process(@NonNull @NotNull HttpRequest<?> httpRequest,
                        @NonNull @NotNull @Valid EmailRequest emailRequest) {
        for (Subscriber subscriber : subscriberService.findAll()) {
            Email email = compose(httpRequest, subscriber, emailRequest);
            emailSender.sendEmail(email);
        }
    }

    @NonNull
    private Optional<String> unsubscribeLink(@NonNull HttpRequest<?> httpRequest,
                                   @NonNull Subscriber subscriber) {
        return confirmationCodeGenerator.generate(subscriber.getEmail()).map(token -> {
            String host = httpHostResolver.resolve(httpRequest);
            return UriBuilder.of(host)
                    .path("/unsubscribe")
                    .queryParam("token", token)
                    .build()
                    .toString();
        });
    }

    @NonNull
    private Email compose(@NonNull HttpRequest<?> httpRequest,
                          @NonNull Subscriber subscriber,
                          @NonNull EmailRequest emailRequest) {
        String text = emailRequest.getText();
        String html = emailRequest.getHtml();
        if (text != null || html != null) {
            Optional<String> unsubscribeLinkOptional = unsubscribeLink(httpRequest, subscriber);
            if (unsubscribeLinkOptional.isPresent()) {
                String unsubscribeLink = unsubscribeLinkOptional.get();
                if (text != null) {
                    text = text.replaceAll(PLACEHOLDER_UNSUBSCRIBE, unsubscribeLink);
                }

                if (html != null) {
                    html = html.replaceAll(PLACEHOLDER_UNSUBSCRIBE, unsubscribeLink);
                }
            }
        }

        return Email.builder()
                .to(subscriber.getEmail())
                .from(emailConfiguration.getFrom())
                .subject(emailRequest.getSubject())
                .text(text)
                .html(html)
                .build();
    }
}
