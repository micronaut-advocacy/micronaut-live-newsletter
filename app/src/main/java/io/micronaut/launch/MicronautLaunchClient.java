package io.micronaut.launch;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

@Client(id = "micronautlaunch")
public interface MicronautLaunchClient extends VersionsClient {
    @Override
    @Get("/versions")
    @SingleResult
    Publisher<VersionsResponse> fetch();
}
