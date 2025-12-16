package org.ejfa;

import java.time.LocalDateTime;

import org.ejfa.dto.CheckoutRequest;
import org.ejfa.model.Order;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/checkout")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CheckoutResource {

    @POST
    @Transactional
    public Response checkout(CheckoutRequest req) {
        if (req == null || req.productId == null || req.userId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("productId and userId are required").build();
        }

        Order order = new Order();
        order.productId = req.productId;
        order.userId = req.userId;
        order.quantity = (req.quantity == null || req.quantity < 1) ? 1 : req.quantity;
        double price = (req.pricePerUnit == null) ? 0.0 : req.pricePerUnit;
        order.totalPrice = order.quantity * price;
        order.status = "COMPLETED";
        order.createdAt = LocalDateTime.now();

        order.persist();

        return Response.status(Response.Status.CREATED).entity(order).build();
    }
}
