package kr.magicbox.orchestrator.adapter.out.persistence.repository;

import kr.magicbox.orchestrator.adapter.out.persistence.entity.OrchestratorInboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrchestratorInboxRepository extends JpaRepository<OrchestratorInboxEntity, Long> {

    boolean existsByKey(String key);

    Optional<OrchestratorInboxEntity> findByTopicAndPartitionAndOffset(String topic, Integer partition, Long offset);
}
