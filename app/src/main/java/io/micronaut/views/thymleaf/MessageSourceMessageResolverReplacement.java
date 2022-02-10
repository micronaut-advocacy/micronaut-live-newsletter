package io.micronaut.views.thymleaf;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.views.thymeleaf.MessageSourceMessageResolver;
import jakarta.inject.Singleton;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.util.Validate;

import java.util.HashMap;
import java.util.Map;

@Replaces(MessageSourceMessageResolver.class)
@Singleton
public class MessageSourceMessageResolverReplacement extends AbstractMessageResolver {
    private final StandardMessageResolver standardMessageResolver;
    private final MessageSource messageSource;

    /**
     * Constructor which will initialize the Thymeleaf standard message resolver, and also the message source for
     * Micronaut.
     *
     * @param messageSource Micronaut message source
     */
    public MessageSourceMessageResolverReplacement(MessageSource messageSource) {
        this.standardMessageResolver = new StandardMessageResolver();
        this.messageSource = messageSource;
    }

    @Override
    public final String resolveMessage(final ITemplateContext context,
                                       final Class<?> origin,
                                       final String key,
                                       final Object[] messageParameters) {

        Validate.notNull(context.getLocale(), "Locale in context cannot be null");
        Validate.notNull(key, "Message key cannot be null");

        MessageSource.MessageContext messageContext = MessageSource.MessageContext.of(context.getLocale(),  translateMessagesToMap(messageParameters));

        return messageSource.getMessage(key, messageContext)
                .map(template -> messageSource.interpolate(template, messageContext))
                .orElse(null);
    }

    @Override
    public String createAbsentMessageRepresentation(final ITemplateContext context,
                                                    final Class<?> origin,
                                                    final String key,
                                                    final Object[] messageParameters) {

        return standardMessageResolver.createAbsentMessageRepresentation(context, origin, key, messageParameters);
    }

    /**
     * Turn the object of message parameters into a map so the message source can interpolate the template message.
     *
     * @param messageParameters variables to fill the template
     * @return map of messageParameter with the key being the index
     */
    private Map<String, Object> translateMessagesToMap(Object[] messageParameters) {
        if (messageParameters.length > 0) {
            int i;
            Map<String, Object> messageMap = new HashMap<>();
            for (i = 0; i < messageParameters.length; i++) {
                String value = (String) messageParameters[i];
                messageMap.put(String.valueOf(i), value);
            }
            return messageMap;
        } else {
            return null;
        }
    }
}
