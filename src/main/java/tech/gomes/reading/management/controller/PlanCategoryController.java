package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.domain.PlanCategory;
import tech.gomes.reading.management.exception.ReadingPlanException;
import tech.gomes.reading.management.service.PlanCategoryService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan/category")
public class PlanCategoryController {

    private final PlanCategoryService service;

    @GetMapping("/")
    ResponseEntity<List<PlanCategory>> getAllCategories() {
        return ResponseEntity.ok(service.findAllCategories());
    }

    @PostMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<List<PlanCategory>> createNewCategories(Set<String> categories) throws ReadingPlanException {
        return ResponseEntity.ok(service.createNewCategories(categories));
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<PlanCategory> updateCategory(@PathVariable long id, String categoryName) throws ReadingPlanException {

        return ResponseEntity.ok(service.updateCategory(id, categoryName));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<Void> deleteCategory(@PathVariable long id) throws ReadingPlanException {

        service.deleteCategory(id);

        return ResponseEntity.ok(null);
    }
}
