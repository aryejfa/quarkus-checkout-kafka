package org.ejfa.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

import org.ejfa.domain.Order;
import org.ejfa.event.OrderCreatedEvent;
import org.ejfa.messaging.OrderProducer;

@Path("/checkout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckoutResource {

    @Inject
    OrderProducer orderProducer;

    @POST
    @Transactional
    public Response checkout(Order order) {

        // 1. Simpan ke database
        order.status = "CREATED";
        order.persist();

        // 2. CONVERT Entity → Event (INI YANG SEBELUMNYA SALAH)
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.id,
                order.userId,
                order.productId,
                order.quantity,
                order.totalPrice
        );

        // 3. Publish ke Kafka
        orderProducer.send(event);

        return Response.ok(order).build();
    }
}
