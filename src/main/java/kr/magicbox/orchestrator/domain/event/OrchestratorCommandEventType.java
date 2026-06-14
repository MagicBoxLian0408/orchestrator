package kr.magicbox.orchestrator.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrchestratorCommandEventType {
    STOCK_RESERVE("stock-reserve"),
    STOCK_RELEASE("stock-release"),
    PAYMENT_APPROVE("payment-approve"),
    ORDER_PREPARE("order-prepare"),
    PAYMENT_CANCEL("payment-cancel"),
    SETTLEMENT_READY("settlement-ready"),
    SETTLEMENT_SETTLE("settlement-settle");

    private final String topicSuffix;
}
