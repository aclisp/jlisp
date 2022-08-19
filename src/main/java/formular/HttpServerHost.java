package formular;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import formular.engine.Default;
import formular.engine.Expression;
import formular.formatter.Formatter;
import formular.parser.Json;
import formular.parser.Symbolic;
import formular.parser.json.Node;
import formular.runner.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class HttpServerHost extends AbstractVerticle {
    private static final Logger LOGGER = Logger.getLogger(HttpServerHost.class.getName());
    private Runner runner = new Runner();
    private Formatter fmt = new Formatter();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        setupRoute(router);
        int port = 8080;
        server.requestHandler(router).listen(port);
        LOGGER.info("Listening on " + port);
    }
    private void setupRoute(Router router) {
        router.get("/").handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.end("Hello!");
        });
        router.get("/formular/json").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                String code = body.toString().trim();
                Expression expr = Symbolic.parse(code);
                Node node = Json.serialize(expr);
                HttpServerResponse res = ctx.response();
                try {
                    String json = objectMapper.writeValueAsString(node);
                    res.putHeader("content-type", "application/json");
                    res.end(json);
                } catch (JsonProcessingException e) {
                    endWithException(res, e);
                }
            });
        });
        router.post("/formular/eval").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                String code = body.toString().trim();
                HttpServerResponse res = ctx.response();
                res.putHeader("content-type", "text/plain");
                try {
                    Object o = runner.execute(code, Default.environment()).getValue();
                    res.end("Result Object Type  : " + o.getClass().getName() + "\n" +
                        "Result Object Value : " + o);
                } catch (Exception e) {
                    endWithException(res, e);
                }
            });
        });
        router.get("/formular/fmt").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                String code = body.toString().trim();
                HttpServerResponse res = ctx.response();
                res.putHeader("content-type", "text/plain");
                res.end(fmt.format(code));
            });
        });
    }
    private void endWithException(HttpServerResponse res, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        res.putHeader("content-type", "text/plain");
        res.end(sw.toString());
    }
}
