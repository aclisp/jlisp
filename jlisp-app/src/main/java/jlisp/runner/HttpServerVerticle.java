package jlisp.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import jlisp.runner.handler.*;

import java.util.logging.Logger;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = Logger.getLogger(HttpServerVerticle.class.getName());

    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        setupRoute(router);
        int port = 8080;
        server.requestHandler(router).listen(port, res -> {
            if (res.succeeded()) {
                LOGGER.info("Listening on " + port);
            } else {
                LOGGER.info("Can not listen on " + port + ": " + res.cause());
                vertx.close();
            }
        });
    }

    private void setupRoute(Router router) {
        router.route().handler(CorsHandler.create());
        router.get("/").handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.end("Hello!");
        });
        router.post("/jlisp/json").handler(new JsonHandler(objectMapper));
        router.post("/jlisp/eval").handler(new EvalHandler(objectMapper));
        router.post("/jlisp/fmt").handler(new FormatHandler());
        router.post("/jlisp/oneline").handler(new OneLineHandler());
        router.get("/jlisp/complete").handler(new CompletionHandler(objectMapper));
    }
}
