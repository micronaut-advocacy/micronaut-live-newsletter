package com.objectcomputing.newsletter.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

@Introspected
public class Alert {

    @Nullable
    private String primary;

    @Nullable
    private String secondary;

    @Nullable
    private String success;

    @Nullable
    private String danger;

    @Nullable
    private String warning;

    @Nullable
    private String info;

    @Nullable
    private String light;

    @Nullable
    private String dark;

    Alert() {

    }

    @Nullable
    public String getPrimary() {
        return primary;
    }

    public void setPrimary(@Nullable String primary) {
        this.primary = primary;
    }

    @Nullable
    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(@Nullable String secondary) {
        this.secondary = secondary;
    }

    @Nullable
    public String getSuccess() {
        return success;
    }

    public void setSuccess(@Nullable String success) {
        this.success = success;
    }

    @Nullable
    public String getDanger() {
        return danger;
    }

    public void setDanger(@Nullable String danger) {
        this.danger = danger;
    }

    @Nullable
    public String getWarning() {
        return warning;
    }

    public void setWarning(@Nullable String warning) {
        this.warning = warning;
    }

    @Nullable
    public String getInfo() {
        return info;
    }

    public void setInfo(@Nullable String info) {
        this.info = info;
    }

    @Nullable
    public String getLight() {
        return light;
    }

    public void setLight(@Nullable String light) {
        this.light = light;
    }

    @Nullable
    public String getDark() {
        return dark;
    }

    public void setDark(@Nullable String dark) {
        this.dark = dark;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        @Nullable
        private String primary;

        @Nullable
        private String secondary;

        @Nullable
        private String success;

        @Nullable
        private String danger;

        @Nullable
        private String warning;

        @Nullable
        private String info;

        @Nullable
        private String light;

        @Nullable
        private String dark;

        @NonNull
        public Builder primary(@Nullable String primary) {
            this.primary = primary;
            return this;
        }

        @NonNull
        public Builder secondary(@Nullable String secondary) {
            this.secondary = secondary;
            return this;
        }

        @NonNull
        public Builder success(@Nullable String success) {
            this.success = success;
            return this;
        }

        @NonNull
        public Builder danger(@Nullable String danger) {
            this.danger = danger;
            return this;
        }

        @NonNull
        public Builder warning(@Nullable String warning) {
            this.warning = warning;
            return this;
        }

        @NonNull
        public Builder info(@Nullable String info) {
            this.info = info;
            return this;
        }

        @NonNull
        public Builder light(@Nullable String light) {
            this.light = light;
            return this;
        }

        @NonNull
        public Builder dark(@Nullable String dark) {
            this.dark = dark;
            return this;
        }

        @NonNull
        public Alert build() {
            Alert pojo = new Alert();
            pojo.setPrimary(primary);
            pojo.setSecondary(secondary);
            pojo.setSuccess(success);
            pojo.setDanger(danger);
            pojo.setWarning(warning);
            pojo.setInfo(info);
            pojo.setLight(light);
            pojo.setDark(dark);
            return pojo;
        }
    }
}
