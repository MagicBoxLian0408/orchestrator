package kr.magicbox.orchestrator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

/**
 * 재고 원복 커맨드 — payment.failed 또는 payment.cancel.succeeded 수신 시 발행
 */
@Builder
public record StockReleaseCommand(
        @JsonProperty("event_id") Long eventId,
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("items") List<ItemPayload> items,
        @JsonProperty("occurred_at") Instant occurredAt
) implements OrchestratorCommandEvent {

    @Builder
    public record ItemPayload(
            @JsonProperty("product_id") Long productId,
            @JsonProperty("quantity") int quantity
    ) {}

    @Override
    public String key() {
        return orderId.toString();
    }

    @Override
    public OrchestratorCommandEventType eventType() {
        return OrchestratorCommandEventType.STOCK_RELEASE;
    }
}
