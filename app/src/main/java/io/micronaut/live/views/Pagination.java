package io.micronaut.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Introspected
public class Pagination {

    @NonNull
    @NotNull
    private final List<Page> pages;

    public static Pagination of(long count,
                                int max,
                                String path,
                                int currentPage) {
        double maxPages = Math.ceil(((double) count) / (double) max);
        List<Page> pages = new ArrayList<>();
        for (int i = 1;  i <= maxPages; i++) {
            pages.add(new Page(i, path, currentPage == i));
        }
        return new Pagination(pages);
    }

    public Pagination(@NonNull List<Page> pages) {
        this.pages = pages;
        Collections.sort(this.pages);
        if (!this.pages.isEmpty() && !getActivePageNumber().isPresent()) {
            throw new IllegalArgumentException("At least one page should be active");
        }

        if (!this.pages.isEmpty() && this.pages.stream().filter(Page::isActive).count() > 1) {
            throw new IllegalArgumentException("Only one page should be active");
        }
    }

    @NonNull
    public List<Page> getPages() {
        return pages;
    }

    public boolean hasNext() {
        if (pages.isEmpty()) {
            return false;
        }
        Optional<Integer> activePageNumberOptional = getActivePageNumber();
        if (activePageNumberOptional.isPresent()) {
            Integer activePageNumber = activePageNumberOptional.get();
            return activePageNumber < pages.size();
        }
        return false;
    }

    public Optional<Integer> getActivePageNumber() {
        return this.pages.stream()
                .filter(Page::isActive)
                .map(Page::getNumber)
                .findFirst();
    }

    @NonNull
    public Optional<String> getPreviousHref() {

        if (hasPrevious() && getActivePageNumber().isPresent()) {
            return getPageHrefByNumber(getActivePageNumber().get() - 1);
        }
        return Optional.empty();
    }

    @NonNull
    public Optional<String> getNextHref() {
        if (hasNext() && getActivePageNumber().isPresent()) {
            return getPageHrefByNumber(getActivePageNumber().get() + 1);
        }
        return Optional.empty();
    }

    @NonNull
    private Optional<String> getPageHrefByNumber(@NonNull Integer pageNumber) {
        return this.pages.stream()
                .filter(p -> p.getNumber().equals(pageNumber))
                .map(Page::getHref)
                .findFirst();
    }

    public boolean hasPrevious() {
        if (pages.isEmpty()) {
            return false;
        }
        Optional<Integer> activePageNumberOptional = getActivePageNumber();
        if (activePageNumberOptional.isPresent()) {
            Integer activePageNumber = activePageNumberOptional.get();
            return activePageNumber != 1;
        }
        return false;
    }
}
