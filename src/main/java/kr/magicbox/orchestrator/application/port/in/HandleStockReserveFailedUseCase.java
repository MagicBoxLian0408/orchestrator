package kr.magicbox.orchestrator.application.port.in;

public interface HandleStockReserveFailedUseCase {
    void handleStockReserveFailed(Long orderId);
}
