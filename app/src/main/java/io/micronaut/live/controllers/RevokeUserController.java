package io.micronaut.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.live.security.RefreshTokenDataRepository;
import io.micronaut.live.security.RefreshTokenEntity;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Controller("/revoke")
class RevokeUserController {

    private final RefreshTokenDataRepository refreshTokenDataRepository;

    RevokeUserController(RefreshTokenDataRepository refreshTokenDataRepository) {
        this.refreshTokenDataRepository = refreshTokenDataRepository;
    }

    @ExecuteOn(TaskExecutors.IO)
    @PermitAll
    @Post
    void revoke(@Body @NonNull RevokeUser revokeUser) {
        List<RefreshTokenEntity> refreshTokenEntityList = refreshTokenDataRepository.findByUsername(revokeUser.getUsername());
        for (RefreshTokenEntity entity : refreshTokenEntityList) {
            refreshTokenDataRepository.delete(entity);
        }
    }

}
