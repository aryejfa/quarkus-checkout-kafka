package org.ejfa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/health")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response health() {
        return Response.ok("OK").build();
    }

    @Inject
    DataSource dataSource;

    @GET
    @Path("/db")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dbHealth() {
        Map<String, Object> body = new HashMap<>();
        try (Connection c = dataSource.getConnection();
             Statement s = c.createStatement()) {
            boolean ok = false;
            try (ResultSet rs = s.executeQuery("SELECT 1")) {
                if (rs.next()) ok = true;
            }
            body.put("dbConnected", ok);
            // try to count orders if table exists
            try (ResultSet rs2 = s.executeQuery("SELECT COUNT(*) FROM orders")) {
                if (rs2.next()) body.put("ordersCount", rs2.getLong(1));
            } catch (Exception ex) {
                body.put("ordersCount", null);
                body.put("ordersError", ex.getMessage());
            }
            return Response.ok(body).build();
        } catch (Exception e) {
            body.put("dbConnected", false);
            body.put("error", e.getMessage());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(body).build();
        }
    }
}
