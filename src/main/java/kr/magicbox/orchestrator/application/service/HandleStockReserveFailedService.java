package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleStockReserveFailedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * stock.reserve.failed 이벤트 처리
 * 플로우: stock.reserve.failed 수신 → Order 서비스가 자체적으로 STOCK_FAILED 처리 (Orchestrator 추가 액션 없음)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleStockReserveFailedService implements HandleStockReserveFailedUseCase {

    @Override
    @Transactional
    public void handleStockReserveFailed(Long orderId) {
        log.info("[Orchestrator] stock.reserve.failed 처리. orderId={}", orderId);
        // Order 서비스가 stock.reserve.failed 이벤트를 직접 구독하여 STOCK_FAILED로 전이함
        // Orchestrator는 추가 커맨드를 발행하지 않음
    }
}
