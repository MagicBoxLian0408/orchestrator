package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleStockReserveFailedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * stock.reserve.failed 이벤트 처리
 * Order 서비스가 stock-reserve-failed를 직접 구독하여 STOCK_FAILED 처리 — Orchestrator 추가 커맨드 없음
 * 재고 원복 불필요 — 재고 예약 자체가 실패했으므로 롤백할 재고 없음
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleStockReserveFailedService implements HandleStockReserveFailedUseCase {

    @Override
    @Transactional
    public void handleStockReserveFailed(Long orderId) {
        log.info("[Orchestrator] stock.reserve.failed 처리. orderId={} — Order 서비스가 직접 STOCK_FAILED 처리", orderId);
    }
}
