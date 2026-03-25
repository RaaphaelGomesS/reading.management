package tech.gomes.reading.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.BookCategory;

import java.util.Set;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    Set<BookCategory> findByNameIn(Set<String> categoriesNames);
}
