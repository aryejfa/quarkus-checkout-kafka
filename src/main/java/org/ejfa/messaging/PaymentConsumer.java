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

    @Incoming("order_created")
    public void process(OrderCreatedEvent event) {

        PaymentResultEvent result =
                new PaymentResultEvent(event.orderId, "SUCCESS");

        // ✅ PRODUCE VIA EMITTER SAJA
        paymentResultProducer.send(result);
    }
}
