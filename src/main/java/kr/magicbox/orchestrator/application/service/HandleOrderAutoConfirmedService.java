package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleOrderAutoConfirmedUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.SettlementReadyCommand;
import kr.magicbox.orchestrator.domain.event.SettlementSettleCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * order.auto_confirmed 이벤트 처리 (7일 자동 구매확정, orderLine 단위)
 * 플로우: order.auto_confirmed 수신
 *   → settlement-ready 커맨드 발행 (orderLine 단위)
 *   → settlement-settle 커맨드 발행 (orderLine 단위)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleOrderAutoConfirmedService implements HandleOrderAutoConfirmedUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handleOrderAutoConfirmed(Long orderId, Long orderLineId, Long sellerId, long grossAmount) {
        log.info("[Orchestrator] order.auto_confirmed 처리. orderId={}, orderLineId={}", orderId, orderLineId);
        Instant now = Instant.now();
        orchestratorOutboxPort.save(SettlementReadyCommand.builder()
                .orderId(orderId)
                .orderLineId(orderLineId)
                .occurredAt(now)
                .build());
        orchestratorOutboxPort.save(SettlementSettleCommand.builder()
                .orderId(orderId)
                .orderLineId(orderLineId)
                .sellerId(sellerId)
                .grossAmount(grossAmount)
                .occurredAt(now)
                .build());
    }
}
