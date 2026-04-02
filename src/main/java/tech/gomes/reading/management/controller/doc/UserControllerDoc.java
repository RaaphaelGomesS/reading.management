package tech.gomes.reading.management.controller.doc;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.dto.user.ChangePasswordRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.dto.user.UserUpdateRequestDTO;

@RequestMapping("/user")
public interface UserControllerDoc {

    @GetMapping("/")
    ResponseEntity<UserResponseDTO> findUser(JwtAuthenticationToken token);

    @PutMapping("/")
    ResponseEntity<UserResponseDTO> updatedUser(@Valid @RequestBody UserUpdateRequestDTO requestDTO, JwtAuthenticationToken token);

    @PostMapping("/password")
    ResponseEntity<Void> updatePassword(@Valid @RequestBody ChangePasswordRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id, JwtAuthenticationToken token);
}
