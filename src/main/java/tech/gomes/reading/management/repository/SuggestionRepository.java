package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.SuggestionTemplate;

import java.util.Optional;

@Repository
public interface SuggestionRepository extends JpaRepository<SuggestionTemplate, Long> {

    Page<SuggestionTemplate> findByStatusAndBookTemplateIsNotNull(String status, Pageable pageable);

    Page<SuggestionTemplate> findByStatusAndBookTemplateIsNull(String status, Pageable pageable);

    Optional<SuggestionTemplate> findByIdAndBookTemplateIsNotNull(long id);

    Optional<SuggestionTemplate> findByIdAndBookTemplateIsNull(long id);
}
