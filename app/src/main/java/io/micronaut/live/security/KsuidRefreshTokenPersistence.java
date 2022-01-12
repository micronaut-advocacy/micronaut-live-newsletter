package io.micronaut.live.security;

import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Singleton
public class KsuidRefreshTokenPersistence implements RefreshTokenPersistence {
    private final RefreshTokenDataRepository refreshTokenDataRepository;
    private final Scheduler scheduler;

    public KsuidRefreshTokenPersistence(RefreshTokenDataRepository refreshTokenDataRepository,
                                        @Named(TaskExecutors.IO) ExecutorService executorService) {
        this.refreshTokenDataRepository = refreshTokenDataRepository;
        this.scheduler = Schedulers.fromExecutorService(executorService);
    }

    @Override
    public void persistToken(RefreshTokenGeneratedEvent event) {
        RefreshTokenEntity entity = new RefreshTokenEntity(event.getRefreshToken(), event.getAuthentication().getName());
        refreshTokenDataRepository.save(entity);
    }

    @Override
    public Publisher<Authentication> getAuthentication(String refreshToken) {
        return Mono.<Authentication>create(emitter -> {
            Optional<RefreshTokenEntity> refreshTokenEntityOptional = refreshTokenDataRepository.findById(refreshToken);
            if (refreshTokenEntityOptional.isPresent()) {
                emitter.success(Authentication.build(refreshTokenEntityOptional.get().getUsername()));
            } else {
                emitter.success();
            }
        }).subscribeOn(scheduler);
    }
}
