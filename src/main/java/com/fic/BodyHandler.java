//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fic;

import com.networknt.handler.Handler;
import com.networknt.handler.MiddlewareHandler;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.AttachmentKey;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class BodyHandler implements MiddlewareHandler {
    public static final AttachmentKey<Object> REQUEST_BODY_BYTES = AttachmentKey.create(Object.class);
    private volatile HttpHandler next;

    public void handleRequest(HttpServerExchange exchange) throws Exception {
//        String contentType = exchange.getRequestHeaders().getFirst(Headers.CONTENT_TYPE);
//        if (contentType != null && (contentType.startsWith("application/json") || contentType.startsWith("multipart/form-data") || contentType.startsWith("application/x-www-form-urlencoded"))) {
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        exchange.startBlocking();
        try {
            byte[] body = IOUtils.toByteArray(exchange.getInputStream());
            exchange.putAttachment(REQUEST_BODY_BYTES, body);
        } catch (IOException var8) {
            logger.error("IOException: ", var8);
            this.setExchangeStatus(exchange, "ERR10015", "");
            return;
        }
//        }
        Handler.next(exchange, this.next);
    }

    public HttpHandler getNext() {
        return this.next;
    }

    public MiddlewareHandler setNext(HttpHandler next) {
        Handlers.handlerNotNull(next);
        this.next = next;
        return this;
    }

    public boolean isEnabled() {
        return true;
    }

    public void register() {
    }
}
