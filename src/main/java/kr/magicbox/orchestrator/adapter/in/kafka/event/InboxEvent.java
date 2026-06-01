package kr.magicbox.orchestrator.adapter.in.kafka.event;

import java.time.Instant;

public interface InboxEvent {
    Instant occurredAt();
}
