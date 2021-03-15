package domain.enums.payable;

import lombok.Getter;

@Getter
public enum InvoiceHoldType {
    HOLD_01("CANNOT_0_OR_1", "Standard Type Invoice should be more than 0"),
    HOLD_02("SAME_CR_DR", "차대변 금액이 일치하지 않습니다."),

    ;

    private String holdCode;
    private String msg;

    InvoiceHoldType(String holdCode, String msg) {
        this.holdCode = holdCode;
        this.msg = msg;
    }
}
