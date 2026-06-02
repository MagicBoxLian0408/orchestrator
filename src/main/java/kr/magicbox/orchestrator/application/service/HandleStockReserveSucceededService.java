package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.adapter.in.kafka.event.StockReserveSucceededEvent;
import kr.magicbox.orchestrator.application.port.in.HandleStockReserveSucceededUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.PaymentApproveCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * stock.reserve.succeeded 이벤트 처리
 * 플로우: stock.reserve.succeeded 수신 → payment-approve 커맨드 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleStockReserveSucceededService implements HandleStockReserveSucceededUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handleStockReserveSucceeded(Long orderId, Long customerId, Long totalAmount, List<StockReserveSucceededEvent.ItemPayload> items) {
        log.info("[Orchestrator] stock.reserve.succeeded 처리. orderId={}", orderId);
        List<PaymentApproveCommand.ItemPayload> commandItems = items == null ? List.of() : items.stream()
                .map(i -> PaymentApproveCommand.ItemPayload.builder()
                        .orderLineId(i.orderLineId())
                        .sellerId(i.sellerId())
                        .amount(i.amount())
                        .build())
                .toList();
        orchestratorOutboxPort.save(PaymentApproveCommand.builder()
                .eventId(orderId)
                .orderId(orderId)
                .customerId(customerId)
                .amount(totalAmount)
                .items(commandItems)
                .occurredAt(Instant.now())
                .build());
    }
}
