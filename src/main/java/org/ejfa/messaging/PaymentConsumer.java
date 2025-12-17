package org.ejfa.messaging;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.ejfa.event.OrderCreatedEvent;
import org.ejfa.event.PaymentResultEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaymentConsumer {

    @Inject
    PaymentResultProducer paymentResultProducer;

    @Inject
    com.fasterxml.jackson.databind.ObjectMapper mapper;

    @Inject
    org.ejfa.service.PaymentService paymentService;

    @Incoming("order_created")
    public void process(String json) {
        try {
            OrderCreatedEvent event = mapper.readValue(json, OrderCreatedEvent.class);
            System.out.println("PaymentConsumer received Order: " + event.orderId);

            // 1. Simpan ke MySQL via Service
            org.ejfa.domain.Payment payment = paymentService.processPayment(event);
            System.out.println("Payment persisted to MySQL with ID: " + payment.id);

            // 2. Kirim Result Event
            PaymentResultEvent result = new PaymentResultEvent(event.orderId, payment.status);
            paymentResultProducer.send(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
