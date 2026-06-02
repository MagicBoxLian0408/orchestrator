package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandlePaymentCancelFailedUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.OrderCancelFailedCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * payment.cancel.failed 이벤트 처리
 * 보상 트랜잭션: payment-cancel-failed 수신 → order-cancel-failed 커맨드 발행
 * Order 서비스: 주문 상태를 CANCEL_FAILED로 전이 (재고 원복 불필요 — 취소 실패이므로 재고가 이미 반영된 상태)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentCancelFailedService implements HandlePaymentCancelFailedUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handlePaymentCancelFailed(Long orderId, String reason) {
        log.info("[Orchestrator] payment.cancel.failed 처리 — order-cancel-failed 커맨드 발행. orderId={}", orderId);
        orchestratorOutboxPort.save(OrderCancelFailedCommand.builder()
                .eventId(orderId)
                .orderId(orderId)
                .reason(reason)
                .occurredAt(Instant.now())
                .build());
    }
}
