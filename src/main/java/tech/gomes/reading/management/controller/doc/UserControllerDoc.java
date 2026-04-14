package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.dto.user.ChangePasswordRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.dto.user.UserUpdateRequestDTO;

@RequestMapping("/user")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento do usuário.")
public interface UserControllerDoc {

    @GetMapping("/")
    @Operation(summary = "Busca informações do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
            @ApiResponse(responseCode = "401", description = "Token inválido.")
    })
    ResponseEntity<UserResponseDTO> findUser(JwtAuthenticationToken token);

    @PutMapping("/")
    @Operation(summary = "Atualiza as informações do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Usuário já cadastrado com o identificador: "),
            @ApiResponse(responseCode = "401", description = "Token inválido.")
    })
    ResponseEntity<UserResponseDTO> updatedUser(@Valid @RequestBody UserUpdateRequestDTO requestDTO, JwtAuthenticationToken token);

    @PostMapping("/password")
    @Operation(summary = "Atualiza a senha atual do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "A senha está incorreta."),
            @ApiResponse(responseCode = "400", description = "A nova senha é a mesma que a senha atual.")
    })
    ResponseEntity<Void> updatePassword(@Valid @RequestBody ChangePasswordRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta o usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem das categorias encontradas."),
            @ApiResponse(responseCode = "404", description = "O usuário não foi encontrado."),
            @ApiResponse(responseCode = "403", description = "Não possui permissão para deletar o usuário."),
    })
    ResponseEntity<Void> deleteUser(@PathVariable Long id, JwtAuthenticationToken token);
}
