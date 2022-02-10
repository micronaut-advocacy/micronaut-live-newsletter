/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.views.turbo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;

public interface TurboResponse<T> {

    /**
     * Return an {@link io.micronaut.http.HttpStatus#OK} response with a body.
     *
     * @param body The response body
     * @param <T>  The body type
     * @return The ok response
     */
    static <T> MutableHttpResponse<T> ok(T body) {
        return HttpResponse.ok(body)
                .contentType(TurboHttpHeaders.TURBO_STREAM);
    }
}
