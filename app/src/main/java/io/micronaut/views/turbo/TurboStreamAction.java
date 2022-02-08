package io.micronaut.views.turbo;

import io.micronaut.core.annotation.NonNull;

public enum TurboStreamAction {
    APPEND("append"),
    PREPEND("prepend"),
    REPLACE("replace"),
    UPDATE("update"),
    REMOVE("remove"),
    BEFORE("before"),
    AFTER("after");

    @NonNull
    private final String action;

    TurboStreamAction(String action) {
        this.action = action;
    }

    @NonNull
    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return this.action;
    }
}
