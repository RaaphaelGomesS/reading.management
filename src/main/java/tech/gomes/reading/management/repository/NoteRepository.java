package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.dto.note.NoteDTO;
import tech.gomes.reading.management.dto.note.NoteSummaryDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {

    Optional<Note> findByIdAndUserId(long id, long userId);

    List<Note> findAllByTitleInAndUserId(Set<String> titles, long id);

    boolean existsByTitleAndUserId(String title, long id);

    boolean existsByIdAndUserId(long id, long userId);

    @Query("SELECT new tech.gomes.reading.management.dto.note.NoteSummaryDTO(t.id, t.title) FROM note s JOIN s.linkedNotes t WHERE s.id = :sourceNoteId")
    List<NoteSummaryDTO> findAllSummaryTargetNotes(@Param("sourceNoteId") long id);

    @Query("SELECT new tech.gomes.reading.management.dto.note.NoteDTO(t.id, t.title, t.category.name, t.type, t.book.id, t.createdAt, t.updatedAt) FROM note s JOIN s.linkedNotes t WHERE s.id = :sourceNoteId")
    Page<NoteDTO> findAllTargetNotesFromSource(@Param("sourceNoteId") long id, Pageable pageable);

    @Query("SELECT new tech.gomes.reading.management.dto.note.NoteDTO(t.id, t.title, t.category.name, t.type, t.book.id, t.createdAt, t.updatedAt) FROM note s JOIN s.invertedNoteLinks t WHERE s.id = :targetNoteId")
    Page<NoteDTO> findAllSourceNotesFromTarget(@Param("targetNoteId") long id, Pageable pageable);
}
