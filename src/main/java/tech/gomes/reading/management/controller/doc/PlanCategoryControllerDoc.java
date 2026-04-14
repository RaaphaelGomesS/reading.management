package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.domain.PlanCategory;

import java.util.List;
import java.util.Set;

@RequestMapping("/plan/category")
@Tag(name = "Categorias de planos de leituras", description = "Endpoints para gerenciamento das categorias para planos de leituras.")
public interface PlanCategoryControllerDoc {

    @GetMapping("/")
    @Operation(summary = "Busca todas as categórias para plano de leituras.")
    @ApiResponse(responseCode = "200", description = "Listagem das categorias encontradas.")
    ResponseEntity<List<PlanCategory>> getAllCategories();

    @PostMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Cria uma ou mais categorias.", description = "Somente o admin pode criar categorias.")
    @ApiResponse(responseCode = "201", description = "Categorias criadas com sucesso.")
    ResponseEntity<List<PlanCategory>> createNewCategories(Set<String> categoriesNames);

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Atualiza uma categoria.", description = "Somente o admin pode criar uma categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não foi encontrada.")
    })
    ResponseEntity<PlanCategory> updateCategory(@PathVariable long id, String categoryName);

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Deleta uma categoria.", description = "Somente o admin pode deletar uma categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não foi encontrada.")
    })
    ResponseEntity<Void> deleteCategory(@PathVariable long id);
}
