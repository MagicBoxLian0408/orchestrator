package kr.magicbox.orchestrator.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrchestratorCommandEventType {
    STOCK_RESERVE_RELEASE("stock-reserve-release"),
    STOCK_RESERVE_GENERAL_GOOD("stock-reserve.general-good"),
    STOCK_RELEASE("stock-release"),
    PAYMENT_APPROVE("payment-approve"),
    ORDER_PREPARE_CONFIRMED("order-prepare-confirmed"),
    PAYMENT_CANCEL("payment-cancel"),
    SETTLEMENT_READY("settlement-ready"),
    SETTLEMENT_SETTLE("settlement-settle");

    private final String topicSuffix;
}
