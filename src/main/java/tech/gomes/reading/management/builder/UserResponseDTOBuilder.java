package tech.gomes.reading.management.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.user.UserResponseDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDTOBuilder {

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail(), user.getUsername());
    }
}
