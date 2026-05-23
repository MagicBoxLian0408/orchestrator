package kr.magicbox.orchestrator.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderConfirmedEvent(
        @JsonProperty("event_id") Long eventId,
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("seller_id") Long sellerId,
        @JsonProperty("total_amount") Long totalAmount,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {}
