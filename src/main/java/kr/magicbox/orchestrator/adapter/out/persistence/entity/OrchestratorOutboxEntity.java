package kr.magicbox.orchestrator.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orchestrator_outbox")
public class OrchestratorOutboxEntity extends BaseEntity {

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(columnDefinition = "JSON", nullable = false)
    private String payload;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_line_id")
    private Long orderLineId;

    @Builder
    public OrchestratorOutboxEntity(String eventType, String payload, Long orderId, Long orderLineId) {
        this.eventType = eventType;
        this.payload = payload;
        this.orderId = orderId;
        this.orderLineId = orderLineId;
    }
}
