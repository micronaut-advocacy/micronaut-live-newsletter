package com.objectcomputing.newsletter.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.uri.UriBuilder;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

@Introspected
public class Page implements Comparable<Page> {

    @NonNull
    @NotNull
    @Min(1)
    private final Integer number;

    @NonNull
    @NotBlank
    private final Function<Integer, String> uriBuilder;
    
    private final boolean active;

    /**
     *
     * @param number starting from 1
     */
    public Page(@NonNull Integer number,
                @NonNull Function<Integer, String> uriBuilder) {
        this(number, uriBuilder, false);
    }

    public Page(@NonNull Integer number,
                @NonNull Function<Integer, String> uriBuilder,
                boolean active) {
        this.number = number;
        this.uriBuilder = uriBuilder;
        this.active = active;
    }

    @NonNull
    public String getHref() {
        return pageHref(getNumber());
    }

    @NonNull
    private String pageHref(@NonNull Integer page) {
        return uriBuilder.apply(page);
    }

    @NonNull
    public Integer getNumber() {
        return number;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int compareTo(Page o) {
        return getNumber().compareTo(o.getNumber());
    }
}
