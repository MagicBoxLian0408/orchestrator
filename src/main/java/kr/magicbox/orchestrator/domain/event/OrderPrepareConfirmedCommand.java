package kr.magicbox.orchestrator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderPrepareConfirmedCommand(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements OrchestratorCommandEvent {

    @Override
    public String key() {
        return orderId.toString();
    }

    @Override
    public OrchestratorCommandEventType eventType() {
        return OrchestratorCommandEventType.ORDER_PREPARE_CONFIRMED;
    }
}
