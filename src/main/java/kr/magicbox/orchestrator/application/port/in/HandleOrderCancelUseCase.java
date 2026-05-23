package kr.magicbox.orchestrator.application.port.in;

public interface HandleOrderCancelUseCase {
    void handleOrderCancel(Long orderId, Long customerId);
}
