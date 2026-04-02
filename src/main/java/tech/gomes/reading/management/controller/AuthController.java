package tech.gomes.reading.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.controller.doc.AuthControllerDoc;
import tech.gomes.reading.management.dto.auth.LoginRequestDTO;
import tech.gomes.reading.management.dto.auth.LoginResponseDTO;
import tech.gomes.reading.management.dto.user.UserRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.exception.ApplicationException;
import tech.gomes.reading.management.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) throws ApplicationException {
        return ResponseEntity.ok(authService.authenticateUser(requestDTO));
    }

    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO requestDTO) throws ApplicationException {
        return ResponseEntity.ok(authService.registerUser(requestDTO));
    }
}
