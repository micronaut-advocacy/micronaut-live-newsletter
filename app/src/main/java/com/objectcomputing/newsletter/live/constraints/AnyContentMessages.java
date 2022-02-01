package com.objectcomputing.newsletter.live.constraints;

import io.micronaut.context.StaticMessageSource;
import jakarta.inject.Singleton;

@Singleton
public class AnyContentMessages extends StaticMessageSource {

    /**
     * {@link AnyContent} message.
     */
    @SuppressWarnings("WeakerAccess")
    public static final String ANY_CONTENT_MESSAGE = "You have to specify text, html or both";
    /**
     * The message suffix to use.
     */
    private static final String MESSAGE_SUFFIX = ".message";

    /**
     * Default constructor to initialize messages.
     * via {@link #addMessage(String, String)}
     */
    public AnyContentMessages() {
        addMessage(AnyContent.class.getName() + MESSAGE_SUFFIX, ANY_CONTENT_MESSAGE);
    }
}
