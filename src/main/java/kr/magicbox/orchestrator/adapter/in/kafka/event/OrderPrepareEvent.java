package kr.magicbox.orchestrator.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderPrepareEvent(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("seller_id") Long sellerId,
        @JsonProperty("total_amount") Long totalAmount,
        @JsonProperty("items") List<ItemPayload> items,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {

    @Builder
    public record ItemPayload(
            @JsonProperty("product_id") Long productId,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("unit_price") long unitPrice,
            @JsonProperty("product_type") String productType
    ) {}
}
