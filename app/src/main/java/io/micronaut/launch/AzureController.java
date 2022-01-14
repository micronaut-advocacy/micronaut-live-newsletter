package io.micronaut.launch;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.annotation.security.PermitAll;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@PermitAll
@Controller("/azure")
public class AzureController {
    private final static String KEY = "azure.function.version";

    private final VersionsClient versionsClient;

    public AzureController(VersionsClient versionsClient) {
        this.versionsClient = versionsClient;
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    @SingleResult
    Publisher<String> version() {
        return Mono.from(versionsClient.fetch())
                .map(versionsResponse -> versionsResponse.getVersions().get(KEY));
    }
}
