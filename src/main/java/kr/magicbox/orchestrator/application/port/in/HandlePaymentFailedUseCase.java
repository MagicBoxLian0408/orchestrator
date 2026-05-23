package kr.magicbox.orchestrator.application.port.in;

public interface HandlePaymentFailedUseCase {
    void handlePaymentFailed(Long orderId);
}
