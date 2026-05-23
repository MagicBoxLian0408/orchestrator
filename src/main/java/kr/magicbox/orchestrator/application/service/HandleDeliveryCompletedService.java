package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleDeliveryCompletedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * delivery.completed 이벤트 처리
 * 정산은 구매확정(수동) 또는 자동확정(7일) 시점에 시작되므로 여기서는 별도 처리 없음
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleDeliveryCompletedService implements HandleDeliveryCompletedUseCase {

    @Override
    @Transactional
    public void handleDeliveryCompleted(Long orderId, Long orderLineId) {
        log.info("[Orchestrator] delivery.completed 처리. orderId={}, orderLineId={}", orderId, orderLineId);
    }
}
