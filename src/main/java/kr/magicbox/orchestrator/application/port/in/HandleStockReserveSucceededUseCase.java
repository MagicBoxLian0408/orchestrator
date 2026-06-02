package kr.magicbox.orchestrator.application.port.in;

import kr.magicbox.orchestrator.adapter.in.kafka.event.StockReserveSucceededEvent;

import java.util.List;

public interface HandleStockReserveSucceededUseCase {
    void handleStockReserveSucceeded(Long orderId, Long customerId, Long totalAmount, List<StockReserveSucceededEvent.ItemPayload> items);
}
