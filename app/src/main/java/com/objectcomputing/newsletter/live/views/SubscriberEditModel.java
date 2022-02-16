package com.objectcomputing.newsletter.live.views;

import com.objectcomputing.newsletter.live.controllers.subscriber.SubscriberEditForm;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class SubscriberEditModel extends FormModel<SubscriberEditForm> {

    public SubscriberEditModel(@NonNull SubscriberEditForm form) {
        setForm(form);
    }
}
