package formular.runner.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Function;
import formular.parser.Json;
import formular.parser.Symbolic;
import formular.parser.json.Node;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
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
                Expression expr = convertBodyToExpression(ctx, body);
                Expression evaluated = Engine.evaluate(expr, environment);
                String result = convertExpressionToResult(evaluated);
                res.end(result);
            } catch (Exception e) {
                Util.endWithException(res, e);
            }
        });
    }

    private String convertExpressionToResult(Expression evaluated) throws JsonProcessingException {
        Object value = evaluated.getValue();
        String valueType = "Java `null`";
        if (value != null) {
            valueType = value.getClass().getTypeName();
        }
        String result = "Evaluated as `" + evaluated + "` of type " + valueType;
        String valueJson = null;
        if (!(evaluated instanceof Function)) {
            valueJson = objectMapper.writeValueAsString(value);
        }
        if (valueJson != null && !evaluated.toString().equals(valueJson)) {
            result += ", JSON notation is " + valueJson;
        }
        return result;
    }

    private Expression convertBodyToExpression(RoutingContext ctx, Buffer body) throws JsonProcessingException, JsonMappingException {
        Expression expr;
        if ("application/json".equals(ctx.request().getHeader("content-type"))) {
            String json = body.toString();
            Node node = objectMapper.readValue(json, Node.class);
            expr = Json.deserialize(node);
        } else {
            String code = body.toString();
            expr = Symbolic.parse(code);
        }
        return expr;
    }
}

class EvalDebugger implements Debugger {

    @Override
    public void expressionEvaluated(Expression before, Expression after, int depth, long nanoDuration) {

    }

}
