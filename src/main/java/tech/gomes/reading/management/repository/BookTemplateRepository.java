package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.BookTemplate;

import java.util.Optional;

@Repository
public interface BookTemplateRepository extends JpaRepository<BookTemplate, Long> {

    @Query("SELECT b FROM bookTemplate b WHERE (b.ISBN = :identifier OR b.titleAuthor = :identifier) AND b.status = :status")
    Optional<BookTemplate> findByIdentifierAndStatus(@Param("identifier") String identifier, @Param("status") String status);

    Page<BookTemplate> findByStatus(String status, Pageable pageable);

    Optional<BookTemplate> findByIdAndStatus(long id, String status);
}
