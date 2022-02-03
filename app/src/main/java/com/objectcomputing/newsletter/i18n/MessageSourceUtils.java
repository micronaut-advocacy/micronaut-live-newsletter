package com.objectcomputing.newsletter.i18n;

import io.micronaut.core.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

public final class MessageSourceUtils {

    private MessageSourceUtils() {
    }

    @NonNull
    public static Map<String, Object> variables(@NonNull Object... args) {
        Map<String, Object> variables = new HashMap<>();
        int count = 0;
        for (Object value : args) {
            variables.put(""+count, value);
            count++;
        }
        return variables;
    }
}

