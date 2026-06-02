package kr.magicbox.orchestrator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

/**
 * 주문 취소 실패 커맨드 — payment-cancel-failed 수신 시 발행
 * Order 서비스: 주문 상태를 CANCEL_FAILED로 전이
 */
@Builder
public record OrderCancelFailedCommand(
        @JsonProperty("event_id") Long eventId,
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("reason") String reason,
        @JsonProperty("occurred_at") Instant occurredAt
) implements OrchestratorCommandEvent {

    @Override
    public String key() {
        return orderId.toString();
    }

    @Override
    public OrchestratorCommandEventType eventType() {
        return OrchestratorCommandEventType.ORDER_CANCEL_FAILED;
    }
}
