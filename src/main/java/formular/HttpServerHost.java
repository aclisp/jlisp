package formular;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import formular.engine.Default;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Function;
import formular.formatter.Formatter;
import formular.parser.Json;
import formular.parser.Symbolic;
import formular.parser.json.Node;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;

public class HttpServerHost extends AbstractVerticle {
    private static final Logger LOGGER = Logger.getLogger(HttpServerHost.class.getName());
    private Formatter fmt = new Formatter();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private Environment env = Default.environment();
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
        router.post("/formular/json").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                HttpServerResponse res = ctx.response();
                try {
                    String code = body.toString();
                    Expression expr = Symbolic.parse(code);
                    Node node = Json.serialize(expr);
                    String json = objectMapper.writeValueAsString(node);
                    res.putHeader("content-type", "application/json");
                    res.end(json);
                } catch (Exception e) {
                    endWithException(res, e);
                }
            });
        });
        router.post("/formular/eval").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                HttpServerResponse res = ctx.response();
                res.putHeader("content-type", "text/plain");
                try {
                    Expression expr;
                    if ("application/json".equals(ctx.request().getHeader("content-type"))) {
                        String json = body.toString();
                        Node node = objectMapper.readValue(json, Node.class);
                        expr = Json.deserialize(node);
                    } else {
                        String code = body.toString();
                        expr = Symbolic.parse(code);
                    }
                    Expression evaluated = Engine.evaluate(expr, env);
                    Object value = evaluated.getValue();
                    String valueType = "Java `null`";
                    if (value != null) {
                        valueType = value.getClass().getTypeName();
                    }
                    String end = "Evaluated as `" + evaluated + "` of type " + valueType;
                    String valueJson = null;
                    if (!(evaluated instanceof Function)) {
                        valueJson = objectMapper.writeValueAsString(value);
                    }
                    if (valueJson != null && !evaluated.toString().equals(valueJson)) {
                        end += ", JSON notation is " + valueJson;
                    }
                    res.end(end);
                } catch (Exception e) {
                    endWithException(res, e);
                }
            });
        });
        router.post("/formular/fmt").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                HttpServerResponse res = ctx.response();
                res.putHeader("content-type", "text/plain");
                try {
                    String code = body.toString();
                    res.end(fmt.format(code));
                } catch (Exception e) {
                    endWithException(res, e);
                }
            });
        });
        router.post("/formular/oneline").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                HttpServerResponse res = ctx.response();
                res.putHeader("content-type", "text/plain");
                try {
                    String code = body.toString();
                    Expression expr = Symbolic.parse(code);
                    res.end(Symbolic.format(expr, false));
                } catch (Exception e) {
                    endWithException(res, e);
                }
            });
        });
        router.get("/formular/complete").handler(ctx -> {
            String prefix = ctx.request().getParam("prefix", "");
            List<String> completions = env.complete(prefix);
            HttpServerResponse res = ctx.response();
            try {
                String json = objectMapper.writeValueAsString(completions);
                res.putHeader("content-type", "application/json");
                res.end(json);
            } catch (JsonProcessingException e) {
                endWithException(res, e);
            }
        });
    }
    private void endWithException(HttpServerResponse res, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        res.putHeader("content-type", "text/plain");
        res.setStatusCode(500).end(sw.toString());
    }
}
