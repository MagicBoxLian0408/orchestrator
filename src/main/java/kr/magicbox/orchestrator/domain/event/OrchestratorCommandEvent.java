package kr.magicbox.orchestrator.domain.event;

public interface OrchestratorCommandEvent {
    String key();
    OrchestratorCommandEventType eventType();
}
