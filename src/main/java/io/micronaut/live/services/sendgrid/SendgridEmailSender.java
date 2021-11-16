package io.micronaut.live.services.sendgrid;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Personalization;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.model.Email;
import io.micronaut.live.services.EmailSender;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Requires(beans = SendGridConfiguration.class)
@Singleton
public class SendgridEmailSender implements EmailSender {
    private static final Logger LOG = LoggerFactory.getLogger(SendgridEmailSender.class);

    private final SendGrid sendGrid;

    public SendgridEmailSender(SendGridConfiguration sendGridConfiguration) {
        sendGrid = new SendGrid(sendGridConfiguration.getApiKey());
    }

    @Override
    public void sendEmail(@NonNull @NotNull @Valid Email email) {
        try {
            send(createRequest(createMail(email)));
        } catch (IOException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Error sending email", ex);
            }
        }
    }

    @NonNull
    private Mail createMail(@NonNull Email email) {
        Mail mail = new Mail();
        com.sendgrid.helpers.mail.objects.Email from = new com.sendgrid.helpers.mail.objects.Email();
        from.setEmail(email.getFrom());
        mail.from = from;
        mail.addPersonalization(createPersonalization(email));
        contentOfEmail(email).ifPresent(mail::addContent);
        return mail;
    }

    @NonNull
    private Personalization createPersonalization(@NonNull Email email) {
        Personalization personalization = new Personalization();
        personalization.setSubject(email.getSubject());
        com.sendgrid.helpers.mail.objects.Email to = new com.sendgrid.helpers.mail.objects.Email(email.getTo());
        personalization.addTo(to);

        if ( email.getCc() != null ) {
            for ( String cc : email.getCc() ) {
                com.sendgrid.helpers.mail.objects.Email ccEmail = new com.sendgrid.helpers.mail.objects.Email();
                ccEmail.setEmail(cc);
                personalization.addCc(ccEmail);
            }
        }

        if ( email.getBcc()  != null ) {
            for ( String bcc : email.getBcc() ) {
                com.sendgrid.helpers.mail.objects.Email bccEmail = new com.sendgrid.helpers.mail.objects.Email();
                bccEmail.setEmail(bcc);
                personalization.addBcc(bccEmail);
            }
        }
        return personalization;
    }

    @NonNull
    private Request createRequest(@NonNull Mail mail)  throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return request;
    }

    private void send(@NonNull Request request) throws IOException {
        Response response = sendGrid.api(request);
        if (LOG.isInfoEnabled()) {
            LOG.info("Status Code: {}", String.valueOf(response.getStatusCode()));
            LOG.info("Body: {}", response.getBody());
            LOG.info("Headers {}", response.getHeaders()
                    .keySet()
                    .stream()
                    .map(key -> key.toString() + "=" + response.getHeaders().get(key))
                    .collect(Collectors.joining(", ", "{", "}")));
        }

    }

    @NonNull
    private Optional<Content> contentOfEmail(@NonNull Email email) {
        if (email.getText() != null) {
            return Optional.of(new Content("text/plain", email.getText()));
        }
        if (email.getHtml() != null) {
            return Optional.of(new Content("text/html", email.getHtml()));
        }
        return Optional.empty();
    }
}
