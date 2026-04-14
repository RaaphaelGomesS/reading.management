package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.dto.category.CategoryRequestDTO;
import tech.gomes.reading.management.dto.category.CategoryResponseDTO;

import java.util.List;

@RequestMapping("/category")
@Tag(name = "Categorias de anotações", description = "Endpoints para gerenciar categorias do usuário para suas anotações.")
public interface NoteCategoryControllerDoc {

    @PostMapping("/")
    @Operation(summary = "Criar nova categoria do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Já existe uma categoria com esse nome."),
    })
    ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO requestDTO, JwtAuthenticationToken token);

    @GetMapping("/")
    @Operation(summary = "Busca todas as categorias do usuário.")
    @ApiResponse(responseCode = "200", description = "Listagem das categorias.")
    ResponseEntity<List<CategoryResponseDTO>> getAllCategoriesOfUser(JwtAuthenticationToken token);

    @PutMapping("/")
    @Operation(summary = "Busca todas as categorias do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não foi encontrada."),
            @ApiResponse(responseCode = "400", description = "Já existe uma categoria com esse nome."),
    })
    ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody CategoryRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    @Operation(summary = "Busca todas as categorias do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não foi encontrada.")
    })
    ResponseEntity<Void> deleteNoteById(@PathVariable Long id, JwtAuthenticationToken token);
}
