package tech.gomes.reading.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.NoteCategory;

import java.util.Optional;

@Repository
public interface NoteCategoryRepository extends JpaRepository<NoteCategory, Long> {

    Optional<NoteCategory> findByNameAndUserId(String name, long userId);
}
