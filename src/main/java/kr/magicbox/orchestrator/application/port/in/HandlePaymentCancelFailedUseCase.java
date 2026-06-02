package kr.magicbox.orchestrator.application.port.in;

public interface HandlePaymentCancelFailedUseCase {
    void handlePaymentCancelFailed(Long orderId, String reason);
}
