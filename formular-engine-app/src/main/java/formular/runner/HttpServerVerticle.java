package formular.runner;

import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import formular.engine.Default;
import formular.engine.Environment;
import formular.runner.handler.CompletionHandler;
import formular.runner.handler.EvalHandler;
import formular.runner.handler.FormatHandler;
import formular.runner.handler.JsonHandler;
import formular.runner.handler.OneLineHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = Logger.getLogger(HttpServerVerticle.class.getName());

    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private Environment environment = Default.environment();

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
        router.post("/formular/json").handler(new JsonHandler(objectMapper));
        router.post("/formular/eval").handler(new EvalHandler(objectMapper, environment));
        router.post("/formular/fmt").handler(new FormatHandler());
        router.post("/formular/oneline").handler(new OneLineHandler());
        router.get("/formular/complete").handler(new CompletionHandler(objectMapper, environment));
    }
}
