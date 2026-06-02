package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandlePaymentFailedUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.StockReleaseCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * payment.failed 이벤트 처리
 * 보상 트랜잭션: payment-failed 수신 → stock-release 커맨드 발행 (재고 원복)
 * Order 상태 전이(PAYMENT_FAILED)는 Order 서비스가 payment.failed 이벤트를 직접 구독하여 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentFailedService implements HandlePaymentFailedUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handlePaymentFailed(Long orderId) {
        log.info("[Orchestrator] payment.failed 처리 — stock-release 커맨드 발행. orderId={}", orderId);
        orchestratorOutboxPort.save(StockReleaseCommand.builder()
                .eventId(orderId)
                .orderId(orderId)
                .occurredAt(Instant.now())
                .build());
    }
}
