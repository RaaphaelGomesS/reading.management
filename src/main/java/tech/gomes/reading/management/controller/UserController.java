package tech.gomes.reading.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.user.UserRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @PutMapping("/")
    public ResponseEntity<UserResponseDTO> updatedUser(@Valid @RequestBody UserRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(userService.updateUser(requestDTO, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        userService.deleteUser(user, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
