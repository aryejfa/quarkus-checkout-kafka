package org.ejfa.messaging;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ejfa.event.PaymentResultEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaymentResultProducer {

    @Inject
    @Channel("payment-result-out")
    Emitter<PaymentResultEvent> emitter;

    public void send(PaymentResultEvent event) {
        emitter.send(event);
    }
}
