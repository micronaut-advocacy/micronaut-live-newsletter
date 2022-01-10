package io.micronaut.live.security;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.SecurityFilter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class NewsletterUserArgumentBinder implements TypedRequestArgumentBinder<NewsletterUser> {

    public static final String ATTRIBUTE_EMAIL = "email";

    @Override
    public Argument<NewsletterUser> argumentType() {
        return Argument.of(NewsletterUser.class);
    }

    @Override
    public BindingResult<NewsletterUser> bind(ArgumentConversionContext<NewsletterUser> context, HttpRequest<?> source) {
        if (!source.getAttributes().contains(SecurityFilter.KEY)) {
            return BindingResult.UNSATISFIED;
        }
        Optional<Authentication> authenticationOptional = source.getUserPrincipal(Authentication.class);
        return authenticationOptional.isPresent() ?
                () -> Optional.of(newsletterUserOfAuthentication(authenticationOptional.get())) : BindingResult.EMPTY;
    }

    @NonNull
    public static NewsletterUser newsletterUserOfAuthentication(@NonNull Authentication authentication) {
        return new NewsletterUser(authentication.getName(),
                authentication.getRoles(),
                authentication.getAttributes().containsKey(ATTRIBUTE_EMAIL) ? authentication.getAttributes().get(ATTRIBUTE_EMAIL).toString() : null);
    }
}
