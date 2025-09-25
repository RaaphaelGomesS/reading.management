package tech.gomes.reading.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.SuggestionTemplate;

@Repository
public interface SuggestionRepository extends JpaRepository<SuggestionTemplate, Long> {
}
