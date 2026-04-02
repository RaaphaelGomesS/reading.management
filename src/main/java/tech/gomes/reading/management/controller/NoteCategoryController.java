package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.controller.doc.NoteCategoryControllerDoc;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.category.CategoryRequestDTO;
import tech.gomes.reading.management.dto.category.CategoryResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.NoteCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteCategoryController implements NoteCategoryControllerDoc {

    private final AuthService authService;

    private final NoteCategoryService categoryService;

    public ResponseEntity<CategoryResponseDTO> createCategory(CategoryRequestDTO requestDTO, JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        CategoryResponseDTO responseDTO = categoryService.createCategoryIfNotExists(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<List<CategoryResponseDTO>> getAllCategoriesOfUser(JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        List<CategoryResponseDTO> categories = categoryService.getAllCategoriesFromUser(user);

        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<CategoryResponseDTO> updateCategory(CategoryRequestDTO requestDTO, JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        CategoryResponseDTO responseDTO = categoryService.updateCategory(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<Void> deleteNoteById(Long id, JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        categoryService.deleteCategory(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
