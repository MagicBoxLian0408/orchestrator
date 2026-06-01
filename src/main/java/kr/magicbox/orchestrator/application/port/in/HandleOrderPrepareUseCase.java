package kr.magicbox.orchestrator.application.port.in;

import kr.magicbox.orchestrator.adapter.in.kafka.event.OrderPrepareEvent;

import java.util.List;

public interface HandleOrderPrepareUseCase {
    void handleOrderPrepare(Long orderId, Long customerId, Long sellerId, Long totalAmount, List<OrderPrepareEvent.ItemPayload> items);
}
