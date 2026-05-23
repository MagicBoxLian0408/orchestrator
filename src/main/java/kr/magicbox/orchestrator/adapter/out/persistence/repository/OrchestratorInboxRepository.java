package kr.magicbox.orchestrator.adapter.out.persistence.repository;

import kr.magicbox.orchestrator.adapter.out.persistence.entity.OrchestratorInboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrchestratorInboxRepository extends JpaRepository<OrchestratorInboxEntity, Long> {

    @Query("SELECT CASE WHEN EXISTS (SELECT i FROM OrchestratorInboxEntity i WHERE i.eventId = :eventId) THEN true ELSE false END")
    boolean existsByEventId(@Param("eventId") Long eventId);
}
