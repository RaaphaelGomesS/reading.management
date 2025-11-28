package tech.gomes.reading.management.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "TB_JOIN_PLAN_TEMPLATE")
public class JoinPlanTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_category_id")
    private Long id;

    @Column(name = "template_id", nullable = false)
    private long templateId;

    @Column(name = "plan_id", nullable = false)
    private long readingPlan;

    @Column(name = "order", unique = true, nullable = false)
    private int order;

    @Column(name = "description")
    private String description;
}
