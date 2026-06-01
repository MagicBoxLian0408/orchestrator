package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleOrderCancelUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.PaymentCancelCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * order.cancel 이벤트 처리
 * 플로우: order.cancel 수신 → payment-cancel 커맨드 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleOrderCancelService implements HandleOrderCancelUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handleOrderCancel(Long orderId, Long customerId) {
        log.info("[Orchestrator] order.cancel 처리. orderId={}", orderId);
        orchestratorOutboxPort.save(PaymentCancelCommand.builder()
                .eventId(orderId)
                .orderId(orderId)
                .customerId(customerId)
                .occurredAt(Instant.now())
                .build());
    }
}
