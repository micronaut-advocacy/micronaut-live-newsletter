package com.objectcomputing.newsletter.live.graalvm;

import io.micronaut.core.annotation.TypeHint;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.views.Page;
import com.objectcomputing.newsletter.live.views.Pagination;
import com.objectcomputing.newsletter.live.views.SubscriberListModel;
import com.objectcomputing.newsletter.live.views.SubscriberRow;

import java.util.Optional;

@TypeHint(value = {
        Alert.class,
        Optional.class,
        SubscriberRow.class,
        Page.class,
        Pagination.class,
        SubscriberListModel.class
}, accessType = TypeHint.AccessType.ALL_PUBLIC_METHODS)
public class ThymeleafReflectConfig {
}
