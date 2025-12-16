package org.ejfa.dto;

public class CheckoutRequest {
    public Long productId;
    public Long userId;
    public Integer quantity;
    public Double pricePerUnit;

    public CheckoutRequest() {
    }
}
