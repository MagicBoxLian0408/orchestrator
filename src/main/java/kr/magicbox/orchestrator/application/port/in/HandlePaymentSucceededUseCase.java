package kr.magicbox.orchestrator.application.port.in;

public interface HandlePaymentSucceededUseCase {
    void handlePaymentSucceeded(Long orderId, Long customerId);
}
