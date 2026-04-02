package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.controller.doc.PlanCategoryControllerDoc;
import tech.gomes.reading.management.domain.PlanCategory;
import tech.gomes.reading.management.service.PlanCategoryService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class PlanCategoryController implements PlanCategoryControllerDoc {

    private final PlanCategoryService service;

    public ResponseEntity<List<PlanCategory>> getAllCategories() {
        return ResponseEntity.ok(service.findAllCategories());
    }

    public ResponseEntity<List<PlanCategory>> createNewCategories(Set<String> categories) {
        return ResponseEntity.ok(service.createNewCategories(categories));
    }

    public ResponseEntity<PlanCategory> updateCategory(long id, String categoryName) {

        return ResponseEntity.ok(service.updateCategory(id, categoryName));
    }

    public ResponseEntity<Void> deleteCategory(long id) {

        service.deleteCategory(id);

        return ResponseEntity.ok(null);
    }
}
