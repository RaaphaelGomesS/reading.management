package tech.gomes.reading.management.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Builder
@Table(name = "TB_PLAN_CATEGORY")
public class PlanCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_category_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanCategory planCategory = (PlanCategory) o;
        return Objects.equals(name, planCategory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
