package tech.gomes.reading.management.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NoteLinkId implements Serializable {

    @Column(name = "source_note_id")
    private Long sourceNoteId;

    @Column(name = "target_note_id")
    private Long targetNoteId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NoteLinkId that = (NoteLinkId) o;

        return Objects.equals(sourceNoteId, that.sourceNoteId) &&
                Objects.equals(targetNoteId, that.targetNoteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNoteId, targetNoteId);
    }
}


