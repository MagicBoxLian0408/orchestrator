package kr.magicbox.orchestrator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record StockReserveReleaseCommand(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("total_amount") Long totalAmount,
        @JsonProperty("items") List<ItemPayload> items,
        @JsonProperty("occurred_at") Instant occurredAt
) implements OrchestratorCommandEvent {

    @Builder
    public record ItemPayload(
            @JsonProperty("order_line_id") Long orderLineId,
            @JsonProperty("product_id") Long productId,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("unit_price") long unitPrice
    ) {}

    @Override
    public String key() {
        return orderId.toString();
    }

    @Override
    public OrchestratorCommandEventType eventType() {
        return OrchestratorCommandEventType.STOCK_RESERVE_RELEASE;
    }
}
