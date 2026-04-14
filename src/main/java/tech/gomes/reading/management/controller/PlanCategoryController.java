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

        List<PlanCategory> categories = service.findAllCategories();

        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<List<PlanCategory>> createNewCategories(Set<String> categoriesNames) {

        List<PlanCategory> categories = service.createNewCategories(categoriesNames);

        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<PlanCategory> updateCategory(long id, String categoryName) {

        PlanCategory category = service.updateCategory(id, categoryName);

        return ResponseEntity.ok(category);
    }

    public ResponseEntity<Void> deleteCategory(long id) {

        service.deleteCategory(id);

        return ResponseEntity.ok(null);
    }
}
