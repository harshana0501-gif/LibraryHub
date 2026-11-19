package com.vcet.library;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryApiServer {

    static class Book {
        int id;
        String title;
        String author;
        int copies;

        Book(int id, String title, String author, int copies) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.copies = copies;
        }

        String toJson() {
            return String.format(
                "{\"id\":%d,\"title\":\"%s\",\"author\":\"%s\",\"copies\":%d}",
                id, escapeJson(title), escapeJson(author), copies
            );
        }

        private static String escapeJson(String s) {
            return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
        }
    }

    private static final List<Book> books = new ArrayList<>();
    private static final AtomicInteger idGen = new AtomicInteger(1);

    public static void main(String[] args) throws IOException {
        books.add(new Book(idGen.getAndIncrement(), "Clean Code", "Robert C. Martin", 5));
        books.add(new Book(idGen.getAndIncrement(), "Effective Java", "Joshua Bloch", 3));

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/books", LibraryApiServer::handleBooks);
        server.setExecutor(null);
        server.start();
        System.out.println("LibraryApiServer running on http://localhost:8080");
    }

    private static void handleBooks(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);

        String method = exchange.getRequestMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equalsIgnoreCase(method)) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < books.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(books.get(i).toJson());
            }
            sb.append("]");
            sendJson(exchange, 200, sb.toString());
            return;
        }

        if ("POST".equalsIgnoreCase(method)) {
            String body = readBody(exchange.getRequestBody());
            String title = extractString(body, "title");
            String author = extractString(body, "author");
            int copies = extractInt(body, "copies", 1);

            if (title.isEmpty() || author.isEmpty()) {
                sendJson(exchange, 400, "{\"error\":\"Title and author are required\"}");
                return;
            }

            Book created = new Book(idGen.getAndIncrement(), title, author, Math.max(1, copies));
            books.add(created);
            sendJson(exchange, 201, created.toJson());
            return;
        }

        sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String readBody(InputStream is) throws IOException {
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    private static String extractString(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
        return m.find() ? m.group(1).trim() : "";
    }

    private static int extractInt(String json, String key, int defaultVal) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)").matcher(json);
        return m.find() ? Integer.parseInt(m.group(1)) : defaultVal;
    }
}