package org.ejfa.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.ejfa.domain.Order;
import org.ejfa.event.OrderCreatedEvent;
import org.ejfa.messaging.OrderProducer;

@Path("/checkout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckoutResource {

    @Inject
    OrderProducer orderProducer;

    @Inject
    org.ejfa.service.OrderService orderService;

    @POST
    public Response checkout(org.ejfa.dto.CheckoutRequest request) {
        System.out.println("Processing checkout request for product: " + request.productId);

        // 1. Simpan ke database (Isolated Transaction)
        Order order = orderService.createOrder(request);
        System.out.println("Order persisted to DB: " + order.id);

        // 2. CONVERT Entity → Event
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.id,
                order.userId,
                order.productId,
                order.quantity,
                order.totalPrice);

        // 3. Publish ke Kafka
        orderProducer.send(event);
        System.out.println("Event sent to Kafka");

        return Response.ok(order).build();
    }

    @GET
    @Path("/ping")
    public String ping() {
        return "pong";
    }
}
