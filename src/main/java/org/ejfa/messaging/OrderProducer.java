package org.ejfa.messaging;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ejfa.event.OrderCreatedEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderProducer {

    @Inject
    @Channel("order-created-out")
    Emitter<OrderCreatedEvent> emitter;

    public void send(OrderCreatedEvent event) {
        emitter.send(event);
    }
}
