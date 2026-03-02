package tech.gomes.reading.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.ReadingPlan;

import java.util.Optional;

@Repository
public interface ReadingPlanRepository extends JpaRepository<ReadingPlan, Long>, JpaSpecificationExecutor<ReadingPlan> {

    boolean existsByTitleAndUserId(String title, long id);

    Optional<ReadingPlan> findByIdAndUserId(long id, long userId);

    @Query("UPDATE TB_PLAN p SET p.copy_count = p.copy_count + 1 WHERE p.plan_id = :id")
    void increaseCopyCont(@Param("id") long id);
}
