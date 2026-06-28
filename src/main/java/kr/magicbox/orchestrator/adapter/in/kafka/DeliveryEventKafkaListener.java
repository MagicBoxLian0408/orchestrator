package kr.magicbox.orchestrator.adapter.in.kafka;

import kr.magicbox.orchestrator.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.DeliveryCompletedEvent;
import kr.magicbox.orchestrator.adapter.out.persistence.entity.OrchestratorInboxEntity;
import kr.magicbox.orchestrator.adapter.out.persistence.repository.OrchestratorInboxRepository;
import kr.magicbox.orchestrator.application.port.in.HandleDeliveryCompletedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryEventKafkaListener {

    private final HandleDeliveryCompletedUseCase handleDeliveryCompletedUseCase;
    private final OrchestratorInboxRepository orchestratorInboxRepository;

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.delivery-completed", groupId = "orchestrator-service")
    public void handleDeliveryCompleted(ConsumerRecord<String, DeliveryCompletedEvent> consumerRecord) {
        log.info("[Inbox] delivery.completed 이벤트 수신. key={}", consumerRecord.key());
        DeliveryCompletedEvent event = consumerRecord.value();
        handleDeliveryCompletedUseCase.handleDeliveryCompleted(event.orderId(), event.orderLineId());
    }

    @DltHandler
    public void handleDlt(ConsumerRecord<String, ?> consumerRecord) {
        log.error("[Inbox] DLT 전환. topic={}, partition={}, offset={}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
        orchestratorInboxRepository.findByTopicAndPartitionAndOffset(consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset())
                .ifPresent(OrchestratorInboxEntity::markDeadLettered);
    }
}
