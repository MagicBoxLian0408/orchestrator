package kr.magicbox.orchestrator.adapter.out.persistence.repository;

import kr.magicbox.orchestrator.adapter.out.persistence.entity.OrchestratorOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrchestratorOutboxRepository extends JpaRepository<OrchestratorOutboxEntity, Long> {
}
