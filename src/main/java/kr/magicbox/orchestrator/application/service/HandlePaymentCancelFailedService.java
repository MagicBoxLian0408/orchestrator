package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandlePaymentCancelFailedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * payment.cancel.failed 이벤트 처리
 * Order 서비스가 payment-cancel-failed를 직접 구독하여 CANCELLATION_FAILED 처리 — Orchestrator 추가 커맨드 없음
 * 재고 원복 불필요 — 결제 취소 자체가 실패했으므로 재고는 이미 예약된 상태 유지
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentCancelFailedService implements HandlePaymentCancelFailedUseCase {

    @Override
    @Transactional
    public void handlePaymentCancelFailed(Long orderId, String reason) {
        log.info("[Orchestrator] payment.cancel.failed 처리. orderId={} — Order 서비스가 직접 CANCELLATION_FAILED 처리", orderId);
    }
}
