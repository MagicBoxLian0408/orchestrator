package kr.magicbox.orchestrator.adapter.out.persistence;

import tools.jackson.databind.ObjectMapper;
import kr.magicbox.orchestrator.adapter.out.persistence.entity.OrchestratorOutboxEntity;
import kr.magicbox.orchestrator.adapter.out.persistence.repository.OrchestratorOutboxRepository;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.OrchestratorCommandEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrchestratorOutboxAdapter implements OrchestratorOutboxPort {

    private final OrchestratorOutboxRepository orchestratorOutboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(OrchestratorCommandEvent event) {
        orchestratorOutboxRepository.save(OrchestratorOutboxEntity.builder()
                .eventType(event.eventType().getTopicSuffix())
                .payload(objectMapper.writeValueAsString(event))
                .build());
    }
}
