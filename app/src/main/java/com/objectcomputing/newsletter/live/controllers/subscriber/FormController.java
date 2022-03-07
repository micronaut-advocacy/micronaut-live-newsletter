package com.objectcomputing.newsletter.live.controllers.subscriber;

import com.objectcomputing.newsletter.live.controllers.HttpRequestUtils;
import com.objectcomputing.newsletter.live.views.FormModel;
import com.objectcomputing.newsletter.live.views.validation.Errors;
import com.objectcomputing.newsletter.live.views.validation.LocalizedErrorsImpl;
import com.objectcomputing.newsletter.live.views.validation.ViolationUtils;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.turbo.TurboStream;
import io.micronaut.views.turbo.http.TurboMediaType;

import javax.validation.ConstraintViolationException;

import static io.micronaut.views.turbo.http.TurboHttpHeaders.TURBO_FRAME;


public abstract class FormController<T> {
    private final ViewsRenderer<FormModel<T>> viewsRenderer;
    protected final LocalizedMessageSource localizedMessageSource;

    protected FormController(LocalizedMessageSource localizedMessageSource,
                             ViewsRenderer<FormModel<T>> viewsRenderer) {
        this.viewsRenderer = viewsRenderer;
        this.localizedMessageSource = localizedMessageSource;
    }

    @NonNull
    protected abstract ModelAndView<? extends FormModel<T>> turboValidationFailedModelAndView(T form);

    @NonNull
    protected abstract ModelAndView<? extends FormModel<T>> validationFailedModelAndView(T form);

    @Produces(MediaType.TEXT_HTML)
    @Error(exception = ConstraintViolationException.class)
    public HttpResponse<?> onValidationFailed(@NonNull HttpRequest<?> request,
                                              @NonNull @Body T form,
                                              @NonNull ConstraintViolationException ex) {
        ModelAndView<? extends FormModel<T>> modelAndView = getModel(request, ex, form);
        return HttpRequestUtils.accepts(request, TurboMediaType.TURBO_STREAM) ?
                turboResponse(request, modelAndView) :
                HttpResponse.ok(modelAndView);
    }

    @NonNull
    protected ModelAndView<? extends FormModel<T>> getModel(@NonNull HttpRequest<?> request,
                                                            @NonNull ConstraintViolationException ex,
                                                            @NonNull T form) {
        ModelAndView<? extends FormModel<T>> modelAndView = HttpRequestUtils.accepts(request, TurboMediaType.TURBO_STREAM) ?
            turboValidationFailedModelAndView(form) : validationFailedModelAndView(form);
        Errors errors = ViolationUtils.createErrors(ex);
        modelAndView.getModel().ifPresent(m -> m.setErrors(new LocalizedErrorsImpl(localizedMessageSource, errors)));
        return modelAndView;
    }

    protected HttpResponse<?> turboResponse(@NonNull HttpRequest<?>  request,
                                            @NonNull ModelAndView<? extends FormModel<T>> modelAndView) {
        String view = modelAndView.getView().orElse(null);
        FormModel<T> formModel = modelAndView.getModel().orElse(null);
        if (view != null) {
            TurboStream.Builder builder = TurboStream.builder()
                    .template(viewsRenderer.render(view, formModel, request));
            String turboFrame = request.getHeaders().get(TURBO_FRAME);

            if (turboFrame != null) {
                builder = builder.targetDomId(turboFrame);
            }
            return HttpResponse.ok(builder.update());
        }
        return HttpResponse.notFound();
    }
}
