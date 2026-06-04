package kr.magicbox.orchestrator.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record DeliveryCompletedEvent(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("order_line_id") Long orderLineId,
        @JsonProperty("delivery_id") Long deliveryId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {}
