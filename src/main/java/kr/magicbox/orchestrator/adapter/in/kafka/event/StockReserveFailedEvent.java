package kr.magicbox.orchestrator.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record StockReserveFailedEvent(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {}
