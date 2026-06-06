package kr.magicbox.orchestrator.adapter.in.kafka;

import kr.magicbox.orchestrator.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.PaymentCancelFailedEvent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.PaymentCancelSucceededEvent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.PaymentFailedEvent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.PaymentSucceededEvent;
import kr.magicbox.orchestrator.application.port.in.HandlePaymentCancelFailedUseCase;
import kr.magicbox.orchestrator.application.port.in.HandlePaymentCancelSucceededUseCase;
import kr.magicbox.orchestrator.application.port.in.HandlePaymentFailedUseCase;
import kr.magicbox.orchestrator.application.port.in.HandlePaymentSucceededUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventKafkaListener {

    private final HandlePaymentSucceededUseCase handlePaymentSucceededUseCase;
    private final HandlePaymentFailedUseCase handlePaymentFailedUseCase;
    private final HandlePaymentCancelSucceededUseCase handlePaymentCancelSucceededUseCase;
    private final HandlePaymentCancelFailedUseCase handlePaymentCancelFailedUseCase;

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.payment-succeeded", groupId = "orchestrator-service")
    public void handlePaymentSucceeded(ConsumerRecord<String, PaymentSucceededEvent> consumerRecord) {
        log.info("[Inbox] payment.succeeded 이벤트 수신. key={}", consumerRecord.key());
        PaymentSucceededEvent event = consumerRecord.value();
        handlePaymentSucceededUseCase.handlePaymentSucceeded(
                event.orderId(), event.customerId());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.payment-failed", groupId = "orchestrator-service")
    public void handlePaymentFailed(ConsumerRecord<String, PaymentFailedEvent> consumerRecord) {
        log.info("[Inbox] payment.failed 이벤트 수신. key={}", consumerRecord.key());
        handlePaymentFailedUseCase.handlePaymentFailed(consumerRecord.value().orderId());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.payment-cancel-succeeded", groupId = "orchestrator-service")
    public void handlePaymentCancelSucceeded(ConsumerRecord<String, PaymentCancelSucceededEvent> consumerRecord) {
        log.info("[Inbox] payment.cancel.succeeded 이벤트 수신. key={}", consumerRecord.key());
        handlePaymentCancelSucceededUseCase.handlePaymentCancelSucceeded(consumerRecord.value().orderId());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.payment-cancel-failed", groupId = "orchestrator-service")
    public void handlePaymentCancelFailed(ConsumerRecord<String, PaymentCancelFailedEvent> consumerRecord) {
        log.info("[Inbox] payment.cancel.failed 이벤트 수신. key={}", consumerRecord.key());
        PaymentCancelFailedEvent event = consumerRecord.value();
        handlePaymentCancelFailedUseCase.handlePaymentCancelFailed(event.orderId(), event.reason());
    }
}
