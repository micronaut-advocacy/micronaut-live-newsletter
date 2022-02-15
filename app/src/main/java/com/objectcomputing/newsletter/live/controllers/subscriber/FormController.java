package com.objectcomputing.newsletter.live.controllers.subscriber;

import com.objectcomputing.newsletter.live.controllers.HttpRequestUtils;
import com.objectcomputing.newsletter.live.views.validation.Errors;
import com.objectcomputing.newsletter.live.views.validation.FieldError;
import com.objectcomputing.newsletter.live.views.validation.ViolationUtils;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboResponse;
import io.micronaut.views.turbo.TurboStream;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static io.micronaut.views.turbo.TurboHttpHeaders.TURBO_FRAME;

public abstract class FormController<T, M> {
    private final ViewsRenderer<M> viewsRenderer;
    private final LocalizedMessageSource localizedMessageSource;

    protected FormController(LocalizedMessageSource localizedMessageSource,
                             ViewsRenderer<M> viewsRenderer) {
        this.localizedMessageSource = localizedMessageSource;
        this.viewsRenderer = viewsRenderer;
    }

    protected abstract ModelAndView<M> validationFailedModelAndView(T form);

    @Produces(MediaType.TEXT_HTML)
    @Error(exception = ConstraintViolationException.class)
    public HttpResponse<?> onValidationFailed(@NonNull HttpRequest<?> request,
                                              @NonNull @Body T form,
                                              @NonNull ConstraintViolationException ex,
                                              @Nullable @Header(TURBO_FRAME) String turboFrame) {
        Errors errors = ViolationUtils.createErrors(ex);
        BeanWrapper<T> wrapper = BeanWrapper.getWrapper(form);
        BeanIntrospection<T> introspection = wrapper.getIntrospection();
        for (String propertyName : introspection.getPropertyNames()) {
            Optional<List<FieldError>> fieldErrorListOptional = errors.fieldErrorsByFieldName(propertyName);
            List<String> errorMessages = new ArrayList<>();
            if (fieldErrorListOptional.isPresent()) {
                List<FieldError> fieldErrorList = fieldErrorListOptional.get();
                for (FieldError fieldError : fieldErrorList) {
                    errorMessages.add(localizedMessageSource.getMessageOrDefault(fieldError.getCode(), fieldError.getDefaultMessage()));
                }
            }
            if (CollectionUtils.isNotEmpty(errorMessages)) {
                String invalidFeedbackPropertyName = propertyName + "InvalidFeedback";
                if (Arrays.asList(wrapper.getPropertyNames()).contains(invalidFeedbackPropertyName)) {
                    wrapper.setProperty(invalidFeedbackPropertyName, String.join(",", errorMessages));
                }
            }
        }
        form = wrapper.getBean();
        if (HttpRequestUtils.acceptsTurboStream(request)) {
            Optional<HttpResponse<?>> responseOptional = turboResponse(request, form, turboFrame);
            if (responseOptional.isPresent()) {
                return responseOptional.get();
            }
        }
        //TODO
        return HttpResponse.ok();
    }

    protected Optional<HttpResponse<?>> turboResponse(@NonNull HttpRequest<?>  request,
                                                      @NonNull T form,
                                                      @Nullable @Header(TURBO_FRAME) String turboFrame) {
        if (turboFrame != null) {
            ModelAndView<M> modelAndView = validationFailedModelAndView(form);
            if (modelAndView.getView().isPresent()) {
                return Optional.of(TurboResponse.ok(TurboStream
                        .builder()
                        .targetDomId(turboFrame)
                        .template(viewsRenderer.render(modelAndView.getView().get(), modelAndView.getModel().orElse(null), request))
                        .update()));
            }
        }
        return Optional.empty();
    }
}
