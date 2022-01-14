package io.micronaut.launch;

import io.micronaut.core.async.annotation.SingleResult;
import org.reactivestreams.Publisher;

public interface VersionsClient {

    @SingleResult
    Publisher<VersionsResponse> fetch();
}
