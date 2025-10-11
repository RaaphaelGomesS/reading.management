package tech.gomes.reading.management.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "noteLink")
@Table(name = "TB_NOTE_LINK")
public class NoteLink {

    @EmbeddedId
    private NoteLinkId id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sourceNoteId")
    @JoinColumn(name = "source_note_id")
    private Note sourceNote;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("targetNoteId")
    @JoinColumn(name = "target_note_id")
    private Note targetNote;
}