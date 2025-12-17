package org.ejfa.event;

public class OrderCreatedEvent {

    public Long orderId;
    public String userId;
    public String productId;
    public Integer quantity;
    public Double totalPrice;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(
            Long orderId,
            String userId,
            String productId,
            Integer quantity,
            Double totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
