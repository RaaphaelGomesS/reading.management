package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.ReadingPlan;
import tech.gomes.reading.management.repository.projections.PlanSummary;

@Repository
public interface ReadingPlanRepository extends JpaRepository<ReadingPlan, Long>, JpaSpecificationExecutor<ReadingPlan> {

    @Query("SELECT p.id AS id, p.title AS title, p.description AS description FROM readingPlan p WHERE p.user.id = :userId")
    Page<PlanSummary> findAllSummaryUserPlans(@Param("userId") long userId, Pageable pageable);
}
