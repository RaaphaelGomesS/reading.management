package tech.gomes.reading.management.controller.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.dto.category.CategoryRequestDTO;
import tech.gomes.reading.management.dto.category.CategoryResponseDTO;

import java.util.List;

@RequestMapping("/category")
public interface NoteCategoryControllerDoc {

    @PostMapping("/")
    ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO requestDTO, JwtAuthenticationToken token);

    @GetMapping("/")
    ResponseEntity<List<CategoryResponseDTO>> getAllCategoriesOfUser(JwtAuthenticationToken token);

    @PutMapping("/")
    ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody CategoryRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteNoteById(@PathVariable Long id, JwtAuthenticationToken token);
}
