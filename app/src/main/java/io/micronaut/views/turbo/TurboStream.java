package io.micronaut.views.turbo;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.io.Writable;
import io.micronaut.core.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

public final class TurboStream implements Writable {

    @NonNull
    private final TurboStreamAction action;

    /**
     * Target dom id.
     */
    @Nullable
    private final String targetDomId;

    /**
     * Target dom id.
     */
    @Nullable
    private final String targetCssQuerySelector;

    private final Template template;

    TurboStream(@NonNull TurboStreamAction action,
                       @Nullable String targetDomId,
                       @Nullable String targetCssQuerySelector,
                       Template template) {
        this.action = action;
        this.targetDomId = targetDomId;
        this.targetCssQuerySelector = targetCssQuerySelector;
        this.template = template;
    }

    @NonNull
    public TurboStreamAction getAction() {
        return action;
    }

    @NonNull
    public Optional<String> getTargetDomId() {
        return Optional.ofNullable(targetDomId);
    }

    @NonNull
    public Optional<String> getTargetCssQuerySelector() {
        return Optional.ofNullable(targetCssQuerySelector);
    }

    @NonNull
    public Optional<Template> getTemplate() {
        return Optional.ofNullable(template);
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void writeTo(Writer out) throws IOException {
        out.write(toString());
    }

    @Override
    public String toString() {
        String html = "<turbo-stream action=\"" + getAction() + "\" ";
        if (getTargetDomId().isPresent()) {
            html += "target=\""+ getTargetDomId().get() +"\"";
        } else if (getTargetCssQuerySelector().isPresent()) {
            html += "targets=\""+ getTargetCssQuerySelector().get() +"\"";
        }
        html += ">";
        if (getTemplate().isPresent()) {
            Template template = getTemplate().get();
            Optional<String> templateString = template.get();
            if (templateString.isPresent()) {
               html += "<template>";
               html += templateString.get();
               html += "</template>";
            }
        }
       html += "</turbo-stream>";
        return html;
    }

    public static class Builder {
        private TurboStreamAction action;
        private String targetDomId;
        private String targetCssQuerySelector;
        private Template template;

        @NonNull
        public Builder action(@NonNull TurboStreamAction action) {
            this.action = action;
            return this;
        }

        @NonNull
        public Builder targetDomId(@NonNull String targetDomId) {
            this.targetDomId = targetDomId;
            return this;
        }

        @NonNull
        public Builder targetCssQuerySelector(@NonNull String targetCssQuerySelector) {
            this.targetCssQuerySelector = targetCssQuerySelector;
            return this;
        }

        @NonNull
        public Builder template(@NonNull Writable writable){
            this.template = new WritableTemplate(writable);
            return this;
        }

        @NonNull
        public Builder template(@NonNull Template template) {
            this.template = template;
            return this;
        }

        @NonNull
        public Builder template(@NonNull String html) {
            this.template = new StringTemplate(html);
            return this;
        }

        @NonNull
        public TurboStream append() {
            return action(TurboStreamAction.APPEND)
                    .build();
        }

        @NonNull
        public TurboStream prepend() {
            return action(TurboStreamAction.PREPEND)
                    .build();
        }

        @NonNull
        public TurboStream update() {
            return action(TurboStreamAction.UPDATE)
                    .build();
        }

        @NonNull
        public TurboStream remove() {
            return action(TurboStreamAction.REMOVE)
                    .build();
        }

        @NonNull
        public TurboStream after() {
            return action(TurboStreamAction.AFTER)
                    .build();
        }

        @NonNull
        public TurboStream before() {
            return action(TurboStreamAction.BEFORE)
                    .build();
        }

        @NonNull
        public TurboStream replace() {
            return action(TurboStreamAction.REPLACE)
                    .build();
        }

        @NonNull
        public TurboStream build() {
            return new TurboStream(action,
                    targetDomId,
                    targetCssQuerySelector,
                    template);
        }

    }
}
