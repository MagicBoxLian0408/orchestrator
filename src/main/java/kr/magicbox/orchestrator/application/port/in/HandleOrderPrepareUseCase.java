package kr.magicbox.orchestrator.application.port.in;

public interface HandleOrderPrepareUseCase {
    void handleOrderPrepare(Long orderId, Long customerId, Long sellerId, Long totalAmount);
}
