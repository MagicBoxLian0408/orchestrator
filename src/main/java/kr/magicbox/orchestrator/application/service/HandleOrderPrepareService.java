package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.adapter.in.kafka.event.OrderPrepareEvent;
import kr.magicbox.orchestrator.application.port.in.HandleOrderPrepareUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.StockReserveGeneralGoodCommand;
import kr.magicbox.orchestrator.domain.event.StockReserveReleaseCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * order.prepare 이벤트 처리
 * 플로우: order.prepare 수신 → product_type 기준으로 stock-reserve.release 또는 stock-reserve.general-good 커맨드 발행
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
        List<OrderPrepareEvent.ItemPayload> safeItems = items == null ? List.of() : items;

        boolean isRelease = safeItems.stream().anyMatch(i -> "RELEASE".equals(i.productType()));

        if (isRelease) {
            List<StockReserveReleaseCommand.ItemPayload> commandItems = safeItems.stream()
                    .map(i -> StockReserveReleaseCommand.ItemPayload.builder()
                            .orderLineId(i.orderLineId())
                            .productId(i.productId())
                            .quantity(i.quantity())
                            .unitPrice(i.unitPrice())
                            .build())
                    .toList();
            orchestratorOutboxPort.save(StockReserveReleaseCommand.builder()
                    .eventId(orderId)
                    .orderId(orderId)
                    .customerId(customerId)
                    .totalAmount(totalAmount)
                    .items(commandItems)
                    .occurredAt(Instant.now())
                    .build());
        } else {
            List<StockReserveGeneralGoodCommand.ItemPayload> commandItems = safeItems.stream()
                    .map(i -> StockReserveGeneralGoodCommand.ItemPayload.builder()
                            .orderLineId(i.orderLineId())
                            .productId(i.productId())
                            .quantity(i.quantity())
                            .unitPrice(i.unitPrice())
                            .build())
                    .toList();
            orchestratorOutboxPort.save(StockReserveGeneralGoodCommand.builder()
                    .eventId(orderId)
                    .orderId(orderId)
                    .customerId(customerId)
                    .totalAmount(totalAmount)
                    .items(commandItems)
                    .occurredAt(Instant.now())
                    .build());
        }
    }
}
