package kr.magicbox.orchestrator.application.port.in;

public interface HandleOrderAutoConfirmedUseCase {
    void handleOrderAutoConfirmed(Long orderId, Long orderLineId, Long sellerId, long grossAmount);
}
