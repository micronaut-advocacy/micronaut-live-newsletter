package io.micronaut.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.email.BodyType;
import io.micronaut.email.Email;
import io.micronaut.email.EmailSender;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.live.Subscriber;
import io.micronaut.live.api.v1.EmailRequest;
import io.micronaut.live.data.SubscriberService;
import jakarta.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class EmailRequestServiceImpl implements EmailRequestService {

    public static final String PLACEHOLDER_UNSUBSCRIBE = "@unsubscribe@";
    private final SubscriberService subscriberService;
    private final EmailSender<?, ?> emailSender;
    private final HttpHostResolver httpHostResolver;
    private final ConfirmationCodeGenerator confirmationCodeGenerator;

    public EmailRequestServiceImpl(SubscriberService subscriberService,
                                   EmailSender<?, ?> emailSender,
                                   HttpHostResolver httpHostResolver,
                                   ConfirmationCodeGenerator confirmationCodeGenerator) {
        this.subscriberService = subscriberService;
        this.emailSender = emailSender;
        this.httpHostResolver = httpHostResolver;
        this.confirmationCodeGenerator = confirmationCodeGenerator;
    }

    @Override
    public void process(@NonNull @NotNull HttpRequest<?> httpRequest,
                        @NonNull @NotNull @Valid EmailRequest emailRequest) {
        for (Subscriber subscriber : subscriberService.findAll()) {
            Email.Builder email = compose(httpRequest, subscriber, emailRequest);
            emailSender.send(email);
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
    private Email.Builder compose(@NonNull HttpRequest<?> httpRequest,
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
        Email.Builder builder = Email.builder()
                .to(subscriber.getEmail())
                .subject(emailRequest.getSubject());
        if (html != null && text != null) {
            return builder.body(html, text);
        } else if (html != null) {
            return builder.body(html, BodyType.HTML);
        } else if (text != null) {
            return builder.body(text, BodyType.TEXT);
        }
        return builder;

    }
}
