package tech.gomes.reading.management.controller.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.domain.PlanCategory;

import java.util.List;
import java.util.Set;

@RequestMapping("/plan/category")
public interface PlanCategoryControllerDoc {

    @GetMapping("/")
    ResponseEntity<List<PlanCategory>> getAllCategories();

    @PostMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<List<PlanCategory>> createNewCategories(Set<String> categories);

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<PlanCategory> updateCategory(@PathVariable long id, String categoryName);

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<Void> deleteCategory(@PathVariable long id);
}
