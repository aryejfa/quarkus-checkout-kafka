package org.ejfa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.ejfa.domain.Order;
import org.ejfa.dto.CheckoutRequest;

@ApplicationScoped
public class OrderService {

    @Inject
    EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Order createOrder(CheckoutRequest req) {
        // Konversi DTO ke Entity
        Order order = new Order();
        order.userId = String.valueOf(req.userId);
        order.productId = String.valueOf(req.productId);
        order.quantity = req.quantity;
        order.createdAt = java.time.LocalDateTime.now();

        // Hitung total price
        if (req.pricePerUnit != null && req.quantity != null) {
            order.totalPrice = req.quantity * req.pricePerUnit;
        } else {
            order.totalPrice = 0.0;
        }

        order.status = "CREATED";

        // Simpan ke DB
        entityManager.persist(order);

        return order;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void updateStatus(Long orderId, String status) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null) {
            order.status = status;
            entityManager.persist(order);
        }
    }
}
