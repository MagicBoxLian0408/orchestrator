package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleStockReserveFailedUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.OrderFailCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * stock.reserve.failed 이벤트 처리
 * 보상 트랜잭션: stock-reserve-failed 수신 → order-fail 커맨드 발행 (주문 FAILED 처리)
 * 재고 원복 불필요 — 재고 예약 자체가 실패했으므로 롤백할 재고 없음
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleStockReserveFailedService implements HandleStockReserveFailedUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handleStockReserveFailed(Long orderId) {
        log.info("[Orchestrator] stock.reserve.failed 처리 — order-fail 커맨드 발행. orderId={}", orderId);
        orchestratorOutboxPort.save(OrderFailCommand.builder()
                .eventId(orderId)
                .orderId(orderId)
                .reason("재고 예약 실패")
                .occurredAt(Instant.now())
                .build());
    }
}
