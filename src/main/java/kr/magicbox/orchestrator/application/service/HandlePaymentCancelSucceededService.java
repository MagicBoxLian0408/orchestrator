package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandlePaymentCancelSucceededUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.StockReleaseCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * payment.cancel.succeeded 이벤트 처리
 * 보상 트랜잭션: payment-cancel-succeeded 수신 → stock-release 커맨드 발행 (재고 원복)
 * Order 상태 전이(CANCELLED)는 Order 서비스가 payment.cancel.succeeded 이벤트를 직접 구독하여 처리
 * 정산 취소는 Settlement 서비스가 payment.cancel.succeeded를 직접 구독하여 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentCancelSucceededService implements HandlePaymentCancelSucceededUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handlePaymentCancelSucceeded(Long orderId) {
        log.info("[Orchestrator] payment.cancel.succeeded 처리 — stock-release 커맨드 발행. orderId={}", orderId);
        orchestratorOutboxPort.save(StockReleaseCommand.builder()
                .eventId(orderId)
                .orderId(orderId)
                .occurredAt(Instant.now())
                .build());
    }
}
