package kr.magicbox.orchestrator.application.port.in;

public interface HandleOrderPurchaseConfirmedUseCase {
    void handleOrderPurchaseConfirmed(Long orderId, Long orderLineId, Long sellerId, long grossAmount);
}
