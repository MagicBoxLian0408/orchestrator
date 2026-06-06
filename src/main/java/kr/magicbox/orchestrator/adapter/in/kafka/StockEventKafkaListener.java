package kr.magicbox.orchestrator.adapter.in.kafka;

import kr.magicbox.orchestrator.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.StockReserveFailedEvent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.StockReserveSucceededEvent;
import kr.magicbox.orchestrator.application.port.in.HandleStockReserveFailedUseCase;
import kr.magicbox.orchestrator.application.port.in.HandleStockReserveSucceededUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import kr.magicbox.orchestrator.global.exception.BusinessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventKafkaListener {

    private final HandleStockReserveSucceededUseCase handleStockReserveSucceededUseCase;
    private final HandleStockReserveFailedUseCase handleStockReserveFailedUseCase;

    @Idempotent
    @RetryableTopic(dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "-dlt", exclude = {BusinessException.class})
    @KafkaListener(topics = "outbox.event.stock-reserve-succeeded", groupId = "orchestrator-service")
    public void handleStockReserveSucceeded(ConsumerRecord<String, StockReserveSucceededEvent> consumerRecord) {
        log.info("[Inbox] stock.reserve.succeeded 이벤트 수신. key={}", consumerRecord.key());
        StockReserveSucceededEvent event = consumerRecord.value();
        handleStockReserveSucceededUseCase.handleStockReserveSucceeded(
                event.orderId(), event.customerId(), event.totalAmount());
    }

    @Idempotent
    @RetryableTopic(dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "-dlt", exclude = {BusinessException.class})
    @KafkaListener(topics = "outbox.event.stock-reserve-failed", groupId = "orchestrator-service")
    public void handleStockReserveFailed(ConsumerRecord<String, StockReserveFailedEvent> consumerRecord) {
        log.info("[Inbox] stock.reserve.failed 이벤트 수신. key={}", consumerRecord.key());
        handleStockReserveFailedUseCase.handleStockReserveFailed(consumerRecord.value().orderId());
    }
}
