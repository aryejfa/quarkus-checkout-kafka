package org.ejfa.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MockPaymentServer {

    public static void main(String[] args) throws Exception {
        int port = 8800;
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/pay", new PayHandler());
        server.setExecutor(null);
        System.out.println("MockPaymentServer listening on http://127.0.0.1:" + port + "/pay");
        server.start();
    }

    static class PayHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream is = exchange.getRequestBody();
            byte[] body = is.readAllBytes();
            String req = new String(body, StandardCharsets.UTF_8);
            System.out.println("[mock] received: " + req);

            String tx = "tx-" + Instant.now().toEpochMilli();
            String resp = "{\"status\":\"SUCCESS\",\"transactionId\":\"" + tx + "\"}";
            byte[] out = resp.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, out.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(out);
            }
        }
    }
}
