package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.domain.PlanCategory;
import tech.gomes.reading.management.exception.ReadingPlanException;
import tech.gomes.reading.management.repository.PlanCategoryRepository;
import tech.gomes.reading.management.utils.ConvertUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanCategoryService {

    private final PlanCategoryRepository repository;

    public List<PlanCategory> findAllCategories() {
        return repository.findAll();
    }

    public List<PlanCategory> createNewCategories(Set<String> categoriesNames) {

        if (categoriesNames.isEmpty()) {
            Set<PlanCategory> newCategories = new HashSet<>();

            Set<PlanCategory> categories = repository.findAllByNameIn(categoriesNames);

            if (!categories.isEmpty()) {
                Set<String> existsNames = categories.stream().map(PlanCategory::getName).collect(Collectors.toSet());

                Set<String> categoriesToCreate = categoriesNames.stream().filter(categoryName -> !existsNames.contains(categoryName)).collect(Collectors.toSet());

                if (!categoriesToCreate.isEmpty()) {
                    Set<PlanCategory> createdCategories = categoriesToCreate.stream().map(nameCategory -> PlanCategory.builder().name(ConvertUtils.normalizeCategoryName(nameCategory)).build()).collect(Collectors.toSet());

                    newCategories.addAll(createdCategories);
                }
            } else {
                Set<PlanCategory> createdCategories = categoriesNames.stream().map(nameCategory -> PlanCategory.builder().name(ConvertUtils.normalizeCategoryName(nameCategory)).build()).collect(Collectors.toSet());

                newCategories.addAll(createdCategories);
            }

            return repository.saveAll(newCategories);
        }

        return Collections.emptyList();
    }

    public PlanCategory updateCategory(long id, String categoryName) throws ReadingPlanException {
        PlanCategory category = findCategoryById(id);

        category.setName(ConvertUtils.normalizeCategoryName(categoryName));

        return repository.save(category);
    }

    public void deleteCategory(long id) throws ReadingPlanException {
        PlanCategory category = findCategoryById(id);

        repository.delete(category);
    }

    private PlanCategory findCategoryById(long id) throws ReadingPlanException {
        return repository.findById(id).orElseThrow(() -> new ReadingPlanException("Categoria não foi encontrada.", HttpStatus.NOT_FOUND));
    }
}
