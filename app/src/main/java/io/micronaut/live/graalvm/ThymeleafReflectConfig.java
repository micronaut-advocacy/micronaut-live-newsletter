package io.micronaut.live.graalvm;

import io.micronaut.core.annotation.TypeHint;
import io.micronaut.live.model.Alert;
import io.micronaut.live.views.Page;
import io.micronaut.live.views.Pagination;
import io.micronaut.live.views.SubscriberListPage;
import io.micronaut.live.views.SubscriberRow;

import java.util.Optional;

@TypeHint(value = {
        Alert.class,
        Optional.class,
        SubscriberRow.class,
        Page.class,
        Pagination.class,
        SubscriberListPage.class
}, accessType = TypeHint.AccessType.ALL_PUBLIC_METHODS)
public class ThymeleafReflectConfig {
}
