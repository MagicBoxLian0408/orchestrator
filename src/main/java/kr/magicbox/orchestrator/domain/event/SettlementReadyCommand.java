package kr.magicbox.orchestrator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record SettlementReadyCommand(
        @JsonProperty("event_id") Long eventId,
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("order_line_id") Long orderLineId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements OrchestratorCommandEvent, OrderLineIdAware {

    @Override
    public String key() {
        return orderLineId.toString();
    }

    @Override
    public OrchestratorCommandEventType eventType() {
        return OrchestratorCommandEventType.SETTLEMENT_READY;
    }
}
