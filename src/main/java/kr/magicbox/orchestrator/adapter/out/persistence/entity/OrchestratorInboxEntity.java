package kr.magicbox.orchestrator.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orchestrator_inbox")
public class OrchestratorInboxEntity extends BaseEntity {

    @Column(name = "event_key", nullable = false, unique = true)
    private String eventKey;

    @Column(nullable = false)
    private String topic;

    @Column(name = "kafka_partition", nullable = false)
    private Integer partition;

    @Column(name = "kafka_offset", nullable = false)
    private Long offset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrchestratorInboxStatus status;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Builder
    public OrchestratorInboxEntity(String eventKey, String topic, Integer partition, Long offset, OrchestratorInboxStatus status, Instant occurredAt) {
        this.eventKey = eventKey;
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.status = status;
        this.occurredAt = occurredAt;
    }

    public void markProcessed() {
        this.status = OrchestratorInboxStatus.PROCESSED;
    }

    public void markDeadLettered() {
        this.status = OrchestratorInboxStatus.DEAD_LETTERED;
    }
}
