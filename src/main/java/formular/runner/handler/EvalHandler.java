package formular.runner.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Function;
import formular.parser.Json;
import formular.parser.Symbolic;
import formular.parser.json.Node;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class EvalHandler implements Handler<RoutingContext> {

    final ObjectMapper objectMapper;
    final Environment environment;

    public EvalHandler(ObjectMapper objectMapper, Environment environment) {
        this.objectMapper = objectMapper;
        this.environment = environment;
    }

    @Override
    public void handle(RoutingContext ctx) {
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
                Expression evaluated = Engine.evaluate(expr, environment);
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
                Util.endWithException(res, e);
            }
        });
    }
}
