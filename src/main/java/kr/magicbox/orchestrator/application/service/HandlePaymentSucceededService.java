package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandlePaymentSucceededUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.OrderPrepareConfirmedCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * payment.succeeded 이벤트 처리
 * 플로우: payment.succeeded 수신 → order-prepare-confirmed 커맨드 발행 (Order: PAYMENT_COMPLETED → PREPARING)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentSucceededService implements HandlePaymentSucceededUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handlePaymentSucceeded(Long orderId, Long customerId) {
        log.info("[Orchestrator] payment.succeeded 처리. orderId={}", orderId);
        orchestratorOutboxPort.save(OrderPrepareConfirmedCommand.builder()
                .orderId(orderId)
                .occurredAt(Instant.now())
                .build());
    }
}
