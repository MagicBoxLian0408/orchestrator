package kr.magicbox.orchestrator.application.port.in;

public interface HandleDeliveryCompletedUseCase {
    void handleDeliveryCompleted(Long orderId, Long orderLineId);
}
