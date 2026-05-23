package kr.magicbox.orchestrator.application.port.out;

import kr.magicbox.orchestrator.domain.event.OrchestratorCommandEvent;

public interface OrchestratorOutboxPort {
    void save(OrchestratorCommandEvent event);
}
