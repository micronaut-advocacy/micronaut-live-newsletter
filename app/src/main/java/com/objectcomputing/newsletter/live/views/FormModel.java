package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class FormModel<T> extends Model {

    private T form;

    public T getForm() {
        return form;
    }

    public void setForm(T form) {
        this.form = form;
    }
}
