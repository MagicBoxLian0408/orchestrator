package kr.magicbox.orchestrator.application.port.in;

public interface HandleOrderConfirmedUseCase {
    void handleOrderConfirmed(Long orderId, Long customerId, Long sellerId, Long totalAmount);
}
