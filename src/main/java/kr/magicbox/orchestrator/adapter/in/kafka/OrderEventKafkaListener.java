package kr.magicbox.orchestrator.adapter.in.kafka;

import kr.magicbox.orchestrator.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.orchestrator.adapter.in.kafka.event.*;
import kr.magicbox.orchestrator.application.port.in.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventKafkaListener {

    private final HandleOrderPrepareUseCase handleOrderPrepareUseCase;
    private final HandleOrderConfirmedUseCase handleOrderConfirmedUseCase;
    private final HandleOrderCancelUseCase handleOrderCancelUseCase;
    private final HandleOrderPurchaseConfirmedUseCase handleOrderPurchaseConfirmedUseCase;
    private final HandleOrderAutoConfirmedUseCase handleOrderAutoConfirmedUseCase;

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.order-prepare", groupId = "orchestrator-service")
    public void handleOrderPrepare(ConsumerRecord<String, OrderPrepareEvent> consumerRecord) {
        log.info("[Inbox] order.prepare 이벤트 수신. eventId={}", consumerRecord.key());
        OrderPrepareEvent event = consumerRecord.value();
        handleOrderPrepareUseCase.handleOrderPrepare(
                event.orderId(), event.customerId(), event.sellerId(), event.totalAmount());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.order-confirmed", groupId = "orchestrator-service")
    public void handleOrderConfirmed(ConsumerRecord<String, OrderConfirmedEvent> consumerRecord) {
        log.info("[Inbox] order.confirmed 이벤트 수신. eventId={}", consumerRecord.key());
        OrderConfirmedEvent event = consumerRecord.value();
        handleOrderConfirmedUseCase.handleOrderConfirmed(
                event.orderId(), event.customerId(), event.sellerId(), event.totalAmount());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.order-cancel", groupId = "orchestrator-service")
    public void handleOrderCancel(ConsumerRecord<String, OrderCancelEvent> consumerRecord) {
        log.info("[Inbox] order.cancel 이벤트 수신. eventId={}", consumerRecord.key());
        OrderCancelEvent event = consumerRecord.value();
        handleOrderCancelUseCase.handleOrderCancel(event.orderId(), event.customerId());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.order-purchase-confirmed", groupId = "orchestrator-service")
    public void handleOrderPurchaseConfirmed(ConsumerRecord<String, OrderPurchaseConfirmedEvent> consumerRecord) {
        log.info("[Inbox] order.purchase_confirmed 이벤트 수신. eventId={}", consumerRecord.key());
        OrderPurchaseConfirmedEvent event = consumerRecord.value();
        handleOrderPurchaseConfirmedUseCase.handleOrderPurchaseConfirmed(
                event.orderId(), event.orderLineId(), event.sellerId(), event.grossAmount());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.order-auto-confirmed", groupId = "orchestrator-service")
    public void handleOrderAutoConfirmed(ConsumerRecord<String, OrderAutoConfirmedEvent> consumerRecord) {
        log.info("[Inbox] order.auto_confirmed 이벤트 수신. eventId={}", consumerRecord.key());
        OrderAutoConfirmedEvent event = consumerRecord.value();
        handleOrderAutoConfirmedUseCase.handleOrderAutoConfirmed(
                event.orderId(), event.orderLineId(), event.sellerId(), event.grossAmount());
    }
}
