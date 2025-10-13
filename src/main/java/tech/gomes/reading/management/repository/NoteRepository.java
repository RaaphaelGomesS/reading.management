package tech.gomes.reading.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.dto.note.NoteSummaryDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByTitleAndUserId(String title, long userId);

    Optional<Note> findByIdAndUserId(long id, long userId);

    List<Note> findAllByTitleInAndUserId(Set<String> titles, long id);

    boolean existsByTitleAndUserId(String title, long id);

    @Query("SELECT new tech.gomes.reading.management.dto.note.NoteSummaryDTO(n.id, n.title) FROM note n INNER JOIN noteLink l ON n.id = :sourceNoteId")
    List<NoteSummaryDTO> findAllLinkedNotes(@Param("sourceNoteId") long id);

    @Query("SELECT new tech.gomes.reading.management.dto.note.NoteSummaryDTO(n.id, n.title) FROM note n INNER JOIN noteLink l ON n.id = :targetNoteId")
    List<NoteSummaryDTO> findAllReversedLinkedNotes(@Param("targetNoteId") long id);
}
