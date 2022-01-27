package io.micronaut.i18n;

import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class MessagesTypedRequestArgumentBinder implements TypedRequestArgumentBinder<Messages> {
    private final MessagesBuilder messagesBuilder;
    private final Argument<Messages> argumentType;

    public MessagesTypedRequestArgumentBinder(MessagesBuilder messagesBuilder) {
        this.messagesBuilder = messagesBuilder;
        this.argumentType = Argument.of(Messages.class);
    }

    @Override
    public Argument<Messages> argumentType() {
        return argumentType;
    }

    @Override
    public BindingResult<Messages> bind(ArgumentConversionContext<Messages> context, HttpRequest<?> source) {
        return () -> Optional.of(messagesBuilder.build(source));
    }
}
