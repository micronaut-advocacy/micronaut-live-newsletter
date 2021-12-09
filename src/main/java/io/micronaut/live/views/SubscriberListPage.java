package io.micronaut.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

@Introspected
public class SubscriberListPage {

    @NonNull
    @NotNull
    private final List<SubscriberRow> rows;

    public SubscriberListPage(@NonNull List<SubscriberRow> rows) {
        this.rows = rows;
    }

    @NonNull
    public List<SubscriberRow> getRows() {
        return rows;
    }
}
