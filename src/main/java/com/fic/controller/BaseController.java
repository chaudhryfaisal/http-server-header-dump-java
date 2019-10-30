
package com.fic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class BaseController implements HttpHandler {

    protected abstract void handleRequest(String method, String path, HttpServerExchange exchange) throws JsonProcessingException;

    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        String path = exchange.getRequestPath();
        String method = exchange.getRequestMethod().toString();
        this.handleRequest(method, path, exchange);

    }
}
