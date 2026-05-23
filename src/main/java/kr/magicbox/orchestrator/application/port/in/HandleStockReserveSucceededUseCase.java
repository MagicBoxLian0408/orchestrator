package kr.magicbox.orchestrator.application.port.in;

public interface HandleStockReserveSucceededUseCase {
    void handleStockReserveSucceeded(Long orderId, Long customerId, Long totalAmount);
}
