package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByBookTemplateIdAndUserId(long templateId, long userId);

    Optional<Book> findByIdAndUserId(long id, long userId);

    Page<Book> findAllByLibraryIdAndStatus(long id, ReadingStatusIndicator status, Pageable pageable);
}
