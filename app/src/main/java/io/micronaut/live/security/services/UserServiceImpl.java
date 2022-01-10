package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.security.entities.UserDataService;
import io.micronaut.live.security.entities.UserEntity;
import io.micronaut.live.security.model.UserState;
import io.micronaut.live.services.IdGenerator;
import jakarta.inject.Singleton;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Singleton
public class UserServiceImpl implements UserService {
    private final IdGenerator idGenerator;
    private final PasswordEncoderService passwordEncoderService;
    private final UserDataService userDataService;

    public UserServiceImpl(IdGenerator idGenerator, PasswordEncoderService passwordEncoderService, UserDataService userDataService) {
        this.idGenerator = idGenerator;
        this.passwordEncoderService = passwordEncoderService;
        this.userDataService = userDataService;
    }

    @Override
    @NonNull
    public Optional<String> save(@NonNull @NotBlank @Email String email,
                                    @NonNull @NotBlank String rawPassword) {
        return idGenerator.generate().map(id -> {
            String password = passwordEncoderService.encode(rawPassword);
            UserEntity userEntity = new UserEntity(id, email, password);
            userDataService.save(userEntity);
            return id;
        });
    }

    @NonNull
    public Boolean existsByEmail(@NonNull @NotBlank @Email String email) {
        return userDataService.countByEmail(email) > 0;
    }
}
