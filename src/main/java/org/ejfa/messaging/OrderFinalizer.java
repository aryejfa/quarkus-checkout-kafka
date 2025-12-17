package org.ejfa.messaging;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.ejfa.event.PaymentResultEvent;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderFinalizer {

    @Incoming("payment_result")
    public void finalizeOrder(PaymentResultEvent event) {
        System.out.println(
            "Order " + event.orderId + " finalized with status " + event.status
        );
    }
}
