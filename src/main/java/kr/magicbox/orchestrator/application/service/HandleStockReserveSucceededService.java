package kr.magicbox.orchestrator.application.service;

import kr.magicbox.orchestrator.application.port.in.HandleStockReserveSucceededUseCase;
import kr.magicbox.orchestrator.application.port.out.OrchestratorOutboxPort;
import kr.magicbox.orchestrator.domain.event.PaymentApproveCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * stock.reserve.succeeded 이벤트 처리
 * 플로우: stock.reserve.succeeded 수신 → payment-approve 커맨드 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandleStockReserveSucceededService implements HandleStockReserveSucceededUseCase {

    private final OrchestratorOutboxPort orchestratorOutboxPort;

    @Override
    @Transactional
    public void handleStockReserveSucceeded(Long orderId, Long customerId, Long totalAmount) {
        log.info("[Orchestrator] stock.reserve.succeeded 처리. orderId={}", orderId);
        orchestratorOutboxPort.save(PaymentApproveCommand.builder()
                .orderId(orderId)
                .customerId(customerId)
                .amount(totalAmount)
                .occurredAt(Instant.now())
                .build());
    }
}
