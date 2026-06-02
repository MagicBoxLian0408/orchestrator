package kr.magicbox.orchestrator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PaymentApproveCommand(
        @JsonProperty("event_id") Long eventId,
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("total_amount") long amount,
        @JsonProperty("occurred_at") Instant occurredAt
) implements OrchestratorCommandEvent {

    @Override
    public String key() {
        return orderId.toString();
    }

    @Override
    public OrchestratorCommandEventType eventType() {
        return OrchestratorCommandEventType.PAYMENT_APPROVE;
    }
}
