package tech.gomes.reading.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.BookTemplate;

import java.util.Optional;

@Repository
public interface BookTemplateRepository extends JpaRepository<BookTemplate, Long> {

    @Query("SELECT b FROM bookTemplate b WHERE b.isbn = :identifier OR b.titleAuthor = :identifier")
    Optional<BookTemplate> findByIdentifier(@Param("identifier") String identifier);
}
