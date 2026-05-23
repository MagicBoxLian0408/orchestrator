package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleOrderConfirmedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * order.confirmed 이벤트 처리
 * 플로우: order.confirmed 수신 → 상태만 확인
 * 주의: 주문 confirm은 배송 시작을 의미하지 않으며,
 * 배송 시작은 delivery 서비스의 명시적 start 요청으로만 진행된다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleOrderConfirmedService implements HandleOrderConfirmedUseCase {

    @Override
    @Transactional
    public void handleOrderConfirmed(Long orderId, Long customerId, Long sellerId, Long totalAmount) {
        log.info("[Orchestrator] order.confirmed 처리. orderId={}, 배송 시작 커맨드 발행 없음", orderId);
    }
}
