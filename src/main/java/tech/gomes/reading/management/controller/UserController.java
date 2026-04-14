package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.builder.UserResponseDTOBuilder;
import tech.gomes.reading.management.controller.doc.UserControllerDoc;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.user.ChangePasswordRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.dto.user.UserUpdateRequestDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerDoc {

    private final UserService userService;

    private final AuthService authService;

    public ResponseEntity<UserResponseDTO> findUser(JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        UserResponseDTO responseDTO = UserResponseDTOBuilder.from(user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<UserResponseDTO> updatedUser(UserUpdateRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        UserResponseDTO responseDTO = userService.updateUser(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<Void> updatePassword(ChangePasswordRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        userService.updatePassword(requestDTO, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(Long id, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        userService.deleteUser(user, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
