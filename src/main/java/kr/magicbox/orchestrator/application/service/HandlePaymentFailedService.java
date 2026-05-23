package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandlePaymentFailedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * payment.failed 이벤트 처리
 * 플로우: payment.failed 수신 → Order 서비스가 자체적으로 PAYMENT_FAILED 처리 (Orchestrator 추가 액션 없음)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentFailedService implements HandlePaymentFailedUseCase {

    @Override
    @Transactional
    public void handlePaymentFailed(Long orderId) {
        log.info("[Orchestrator] payment.failed 처리. orderId={}", orderId);
        // Order 서비스가 payment.failed 이벤트를 직접 구독하여 PAYMENT_FAILED로 전이함
        // Orchestrator는 추가 커맨드를 발행하지 않음
    }
}
