package org.ejfa.messaging;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.ejfa.event.PaymentResultEvent;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderFinalizer {

    @jakarta.inject.Inject
    com.fasterxml.jackson.databind.ObjectMapper mapper;

    @Incoming("payment_result")
    public void finalizeOrder(String json) {
        try {
            PaymentResultEvent event = mapper.readValue(json, PaymentResultEvent.class);
            System.out.println(
                    "Order " + event.orderId + " finalized with status " + event.status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
