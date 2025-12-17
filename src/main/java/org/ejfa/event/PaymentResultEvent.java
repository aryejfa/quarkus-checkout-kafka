package org.ejfa.event;

public class PaymentResultEvent {

    public Long orderId;
    public String status;

    public PaymentResultEvent() {
    }

    public PaymentResultEvent(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}
