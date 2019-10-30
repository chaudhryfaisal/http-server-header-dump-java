
package com.fic.controller;

import com.fic.BodyHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class AppController extends BaseController {
    @Override
    protected void handleRequest(String method, String path, HttpServerExchange exchange) {
        StringBuilder response = new StringBuilder();
        response.append(method).append(" -> ").append(path).append("\n");
        exchange.getRequestHeaders().forEach(h -> response.append(h.getHeaderName()).append(": ").append(Arrays.toString(h.toArray())).append("\n"));
        byte[] attachment = (byte[]) exchange.getAttachment(BodyHandler.REQUEST_BODY_BYTES);
        if (attachment != null) {
            response.append("Body=").append(new String(attachment)).append("\n");
        }
        log.info("Request START\n{}\nRequest END", response.toString());
        exchange.setStatusCode(200);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send(response.toString());
        exchange.endExchange();
    }

}
