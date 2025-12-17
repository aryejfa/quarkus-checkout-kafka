package org.ejfa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.ejfa.domain.Payment;
import org.ejfa.event.OrderCreatedEvent;
import java.time.LocalDateTime;

@ApplicationScoped
public class PaymentService {

    @Inject
    EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Payment processPayment(OrderCreatedEvent event) {
        // Logika Bisnis: Buat Payment record di MySQL
        Payment payment = new Payment();
        payment.orderId = event.orderId;
        payment.amount = event.totalPrice;
        payment.status = "SUCCESS"; // Simulasi sukses
        payment.createdAt = LocalDateTime.now();

        // INSERT into payments ...
        entityManager.persist(payment);

        return payment;
    }
}
