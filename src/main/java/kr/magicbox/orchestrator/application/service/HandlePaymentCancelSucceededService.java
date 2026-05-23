package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandlePaymentCancelSucceededUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * payment.cancel.succeeded 이벤트 처리
 * 플로우: payment.cancel.succeeded 수신 → Order 서비스가 자체적으로 CANCELLED 처리 (Orchestrator 추가 액션 없음)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlePaymentCancelSucceededService implements HandlePaymentCancelSucceededUseCase {

    @Override
    @Transactional
    public void handlePaymentCancelSucceeded(Long orderId) {
        log.info("[Orchestrator] payment.cancel.succeeded 처리. orderId={}", orderId);
        // Order 서비스가 payment.cancel.succeeded 이벤트를 직접 구독하여 CANCELLED로 전이함
        // 정산 취소는 Settlement 서비스가 payment.cancel.succeeded를 직접 구독하여 처리
    }
}
