
package com.fic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.body.BodyHandler;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class BaseController implements HttpHandler {

    protected abstract void handleRequest(String var1, String var2, HttpServerExchange var3) throws JsonProcessingException;

    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String path = exchange.getRequestPath();
        String method = exchange.getRequestMethod().toString();
        log.info("{} -> {}", method, path);
        this.handleRequest(method, path, exchange);

    }

    protected void handleInvalidRequest(String method, String path, HttpServerExchange exchange) throws JsonProcessingException {
        this.handleErrorRequest(method, path, "Bad Request", 400, exchange);
    }

    protected void handleUnknownRequest(String method, String path, HttpServerExchange exchange) throws JsonProcessingException {
        this.handleErrorRequest(method, path, "Unknown Path", 404, exchange);
    }

    protected void handleErrorRequest(String method, String path, String msg, int code, HttpServerExchange exchange) throws JsonProcessingException {
        String error = String.format("method=%s, path=%s msg=%s", method, path, msg);
        log.error("handleErrorRequest -> {}", msg);
        this.respond(exchange, error, code);
    }

    protected String postBody(HttpServerExchange exchange) {
        return (String) exchange.getAttachment(BodyHandler.REQUEST_BODY);
    }

    protected void respond(HttpServerExchange exchange, Object o) throws JsonProcessingException {
        this.respond(exchange, o, 200, 404);
    }

    protected void respond(HttpServerExchange exchange, Object o, int successCode) throws JsonProcessingException {
        this.respond(exchange, o, successCode, 404);
    }

    protected void respond(HttpServerExchange exchange, Object o, int successCode, int failureCode) throws JsonProcessingException {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        if (o == null) {
            exchange.setStatusCode(failureCode);
            exchange.endExchange();
        } else {
            exchange.setStatusCode(successCode);
            exchange.getResponseSender().send(o.toString());
        }

    }
}
