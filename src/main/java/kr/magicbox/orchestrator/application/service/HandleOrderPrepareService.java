package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.adapter.in.kafka.event.OrderPrepareEvent;
import kr.magicbox.orchestrator.application.port.in.HandleOrderPrepareUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.StockReserveCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * order.prepare 이벤트 처리
 * 플로우: order.prepare 수신 → stock-reserve 커맨드 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleOrderPrepareService implements HandleOrderPrepareUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handleOrderPrepare(Long orderId, Long customerId, Long sellerId, Long totalAmount, List<OrderPrepareEvent.ItemPayload> items) {
        log.info("[Orchestrator] order.prepare 처리. orderId={}", orderId);
        List<StockReserveCommand.ItemPayload> commandItems = items == null ? List.of() : items.stream()
                .map(i -> StockReserveCommand.ItemPayload.builder()
                        .productId(i.productId())
                        .quantity(i.quantity())
                        .unitPrice(i.unitPrice())
                        .build())
                .toList();
        orchestratorOutboxPort.save(StockReserveCommand.builder()
                .orderId(orderId)
                .customerId(customerId)
                .totalAmount(totalAmount)
                .items(commandItems)
                .occurredAt(Instant.now())
                .build());
    }
}
