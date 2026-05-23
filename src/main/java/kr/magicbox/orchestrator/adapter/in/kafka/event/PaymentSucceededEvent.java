package kr.magicbox.orchestrator.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PaymentSucceededEvent(
        @JsonProperty("event_id") Long eventId,
        @JsonProperty("payment_id") Long paymentId,
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("seller_id") Long sellerId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {}
