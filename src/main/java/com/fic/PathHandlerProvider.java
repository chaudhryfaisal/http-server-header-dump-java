package com.fic;

import com.fic.controller.AppController;
import com.networknt.handler.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.util.Methods;

public class PathHandlerProvider implements HandlerProvider {
    public static final String PATH_HOOK = "/*";

    public HttpHandler getHandler() {
        AppController controller = new AppController();
        return Handlers.routing()
                .add(Methods.POST, PATH_HOOK, controller)
                .add(Methods.GET, PATH_HOOK, controller);
    }
}