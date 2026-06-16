package kr.magicbox.orchestrator.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderAutoConfirmedEvent(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("order_line_id") Long orderLineId,
        @JsonProperty("seller_id") Long sellerId,
        @JsonProperty("gross_amount") Long grossAmount,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {}
