package kr.magicbox.orchestrator.adapter.out.persistence;

import tools.jackson.databind.ObjectMapper;
import kr.magicbox.orchestrator.adapter.out.persistence.entity.OrchestratorOutboxEntity;
import kr.magicbox.orchestrator.adapter.out.persistence.repository.OrchestratorOutboxRepository;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.OrchestratorCommandEvent;
import kr.magicbox.orchestrator.domain.event.OrderLineIdAware;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrchestratorOutboxAdapter implements OrchestratorOutboxPort {

    private final OrchestratorOutboxRepository orchestratorOutboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(OrchestratorCommandEvent event) {
        Long orderLineId = event instanceof OrderLineIdAware e ? e.orderLineId() : null;
        orchestratorOutboxRepository.save(OrchestratorOutboxEntity.builder()
                .eventType(event.eventType().getTopicSuffix())
                .payload(objectMapper.writeValueAsString(event))
                .orderId(event.orderId())
                .orderLineId(orderLineId)
                .build());
    }
}
