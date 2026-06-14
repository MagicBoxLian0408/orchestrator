package kr.magicbox.orchestrator.adapter.out.persistence.repository;

import kr.magicbox.orchestrator.adapter.out.persistence.entity.OrchestratorInboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrchestratorInboxRepository extends JpaRepository<OrchestratorInboxEntity, Long> {

    boolean existsByEventKey(String eventKey);
}
