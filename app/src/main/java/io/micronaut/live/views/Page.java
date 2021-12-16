package io.micronaut.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.uri.UriBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Introspected
public class Page implements Comparable<Page> {

    @NonNull
    @NotNull
    @Min(1)
    private final Integer number;

    @NonNull
    @NotBlank
    private final String path;

    private final boolean active;

    /**
     *
     * @param number starting from 1
     */
    public Page(@NonNull Integer number,
                @NonNull String path) {
        this(number, path, false);
    }

    public Page(@NonNull Integer number,
                @NonNull String path,
                boolean active) {
        this.number = number;
        this.path = path;
        this.active = active;
    }

    @NonNull
    public String getHref() {
        return pageHref(getNumber());
    }

    @NonNull
    private String pageHref(@NonNull Integer page) {
        return UriBuilder.of(path)
                .queryParam("page", page)
                .build()
                .toString();
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
