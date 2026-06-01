package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleOrderPurchaseConfirmedUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.SettlementReadyCommand;
import kr.magicbox.orchestrator.domain.event.SettlementSettleCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * order.purchase_confirmed 이벤트 처리 (orderLine 단위)
 * 플로우: order.purchase_confirmed 수신
 *   → settlement-ready 커맨드 발행 (orderLine 단위)
 *   → settlement-settle 커맨드 발행 (orderLine 단위)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleOrderPurchaseConfirmedService implements HandleOrderPurchaseConfirmedUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handleOrderPurchaseConfirmed(Long orderId, Long orderLineId, Long sellerId, long grossAmount) {
        log.info("[Orchestrator] order.purchase_confirmed 처리. orderId={}, orderLineId={}", orderId, orderLineId);
        Instant now = Instant.now();
        orchestratorOutboxPort.save(SettlementReadyCommand.builder()
                .eventId(orderLineId)
                .orderId(orderId)
                .orderLineId(orderLineId)
                .occurredAt(now)
                .build());
        orchestratorOutboxPort.save(SettlementSettleCommand.builder()
                .eventId(orderLineId)
                .orderId(orderId)
                .orderLineId(orderLineId)
                .sellerId(sellerId)
                .grossAmount(grossAmount)
                .occurredAt(now)
                .build());
    }
}
