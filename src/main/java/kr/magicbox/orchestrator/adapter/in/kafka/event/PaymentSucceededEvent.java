package kr.magicbox.orchestrator.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PaymentSucceededEvent(
        @JsonProperty("payment_id") Long paymentId,
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {}
