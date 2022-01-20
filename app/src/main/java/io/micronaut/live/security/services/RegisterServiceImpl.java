package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Singleton
public class RegisterServiceImpl implements RegisterService {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterServiceImpl.class);
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    public RegisterServiceImpl(UserService userService,
                               RoleService roleService,
                               UserRoleService userRoleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @Transactional
    @Override
    public void register(@NonNull @NotBlank @Email String email,
                         @NonNull@NotBlank String rawPassword,
                         @NonNull List<String> authorities) {

        if (userService.existsByEmail(email)) {
            if (LOG.isInfoEnabled()) {
                LOG.info("User already exists with {}", email);
            }
        } else {
            Optional<String> userIdOptional = userService.save(email, rawPassword);
            if (userIdOptional.isPresent()) {
                String userId = userIdOptional.get();
                for (String authority : authorities) {
                    if (!roleService.existsByAuthority(authority)) {
                        Optional<String> roleIdOptional = roleService.save(authority);
                        if (roleIdOptional.isPresent()) {
                            userRoleService.grant(authority, userId);
                        }
                    }
                }
            }

        }
    }
}
