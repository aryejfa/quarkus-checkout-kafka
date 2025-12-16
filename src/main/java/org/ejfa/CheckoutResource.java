package org.ejfa;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.ejfa.dto.CheckoutRequest;
import org.ejfa.model.Order;
import org.ejfa.model.Payment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
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

    @Inject
    @ConfigProperty(name = "payment.endpoint.url", defaultValue = "http://127.0.0.1:8800/pay")
    String paymentEndpoint;

    private final ObjectMapper mapper = new ObjectMapper();

    @POST
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
        order.status = "PENDING";
        order.createdAt = LocalDateTime.now();

        // persist order in its own transaction
        persistOrder(order);

        // Call payment endpoint
        boolean paymentSuccess = false;
        String paymentResponseRaw = null;
        String paymentStatus = "FAILED";
        try {
            Map<String, Object> paymentReq = Map.of(
                    "orderId", order.id,
                    "amount", order.totalPrice
            );
            String reqBody = mapper.writeValueAsString(paymentReq);
            URL url = new URL(paymentEndpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            byte[] out = reqBody.getBytes(StandardCharsets.UTF_8);
            con.setFixedLengthStreamingMode(out.length);
            con.connect();
            try (OutputStream os = con.getOutputStream()) {
                os.write(out);
            }
            int status = con.getResponseCode();
            InputStream is = (status >= 200 && status < 300) ? con.getInputStream() : con.getErrorStream();
            paymentResponseRaw = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            if (status >= 200 && status < 300) {
                Map<String, Object> resp = mapper.readValue(paymentResponseRaw, new TypeReference<>() {});
                Object st = resp.get("status");
                if (st != null && ("SUCCESS".equalsIgnoreCase(st.toString()) || "COMPLETED".equalsIgnoreCase(st.toString()))) {
                    paymentSuccess = true;
                    paymentStatus = "COMPLETED";
                }
            }
        } catch (Exception e) {
            paymentResponseRaw = e.getMessage();
        }

        if (paymentSuccess) {
            finalizeSuccess(order.id, order.totalPrice, paymentResponseRaw);
            Order updated = Order.findById(order.id);
            return Response.status(Response.Status.CREATED).entity(updated).build();
        } else {
            finalizeFailure(order.id, paymentResponseRaw);
            Order updated = Order.findById(order.id);
            return Response.status(Response.Status.ACCEPTED).entity(updated).build();
        }
    }

    @Transactional
    void persistOrder(Order order) {
        order.persist();
    }

    @Transactional
    void finalizeSuccess(Long orderId, Double amount, String providerResp) {
        Order o = Order.findById(orderId);
        if (o != null) {
            o.status = "COMPLETED";
            o.persist();
        }
        Payment p = new Payment();
        p.orderId = orderId;
        p.amount = amount;
        p.status = "COMPLETED";
        p.providerResponse = providerResp;
        p.createdAt = LocalDateTime.now();
        p.persist();
    }

    @Transactional
    void finalizeFailure(Long orderId, String providerResp) {
        Order o = Order.findById(orderId);
        if (o != null) {
            o.status = "FAILED";
            o.persist();
        }
        Payment p = new Payment();
        p.orderId = orderId;
        p.amount = null;
        p.status = "FAILED";
        p.providerResponse = providerResp;
        p.createdAt = LocalDateTime.now();
        p.persist();
    }
}
