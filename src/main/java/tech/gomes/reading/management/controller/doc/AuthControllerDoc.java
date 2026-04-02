package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.gomes.reading.management.dto.auth.LoginRequestDTO;
import tech.gomes.reading.management.dto.auth.LoginResponseDTO;
import tech.gomes.reading.management.dto.user.UserRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;

@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para cadastro e login dos usuários")
public interface AuthControllerDoc {

    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO);

    @PostMapping("/register")
    ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO requestDTO);
}
