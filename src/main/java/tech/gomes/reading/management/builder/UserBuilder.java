package tech.gomes.reading.management.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.user.UserRequestDTO;
import tech.gomes.reading.management.indicator.RoleIndicator;

@Component
@RequiredArgsConstructor
public class UserBuilder {

    private final PasswordEncoder passwordEncoder;

    public User from(UserRequestDTO requestDTO) {
        return User.builder()
                .email(requestDTO.getEmail())
                .username(requestDTO.getUsername())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(RoleIndicator.DEFAULT)
                .build();
    }
}
