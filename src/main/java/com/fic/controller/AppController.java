
package com.fic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fic.BodyHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class AppController extends BaseController {
    @Override
    protected void handleRequest(String method, String path, HttpServerExchange exchange) throws JsonProcessingException {
        echo(exchange);
    }

    private void echo(HttpServerExchange exchange) {
        exchange.getRequestHeaders().forEach(h -> {
                    log.info("Header IN {}: {}", h.getHeaderName(), h.toArray());
                    exchange.getResponseHeaders().putAll(new HttpString("IN_" + h.getHeaderName()), h);
                }
        );
        byte[] attachment = (byte[]) exchange.getAttachment(BodyHandler.REQUEST_BODY_BYTES);
        String contentType = exchange.getRequestHeaders().getFirst(Headers.CONTENT_TYPE);
        exchange.setStatusCode(200);
        if (contentType != null) {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, contentType);
        }
        if (attachment != null) {
            log.info("body={}\n", new String(attachment));
            exchange.getResponseSender().send(ByteBuffer.wrap(attachment));
        }
        exchange.getResponseHeaders().forEach(h -> {
                    log.info("Header OUT {}: {}", h.getHeaderName(), h.toArray());
                }
        );
        log.info("Request ENDED");
        exchange.endExchange();
    }

}
