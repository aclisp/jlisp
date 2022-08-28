package formular.runner.handler;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Symbol;
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
            res.putHeader("content-type", "application/json");
            try {
                Expression expr = convertBodyToExpression(ctx, body);
                EvalDebugger debugger = new EvalDebugger();
                Expression evaluated = Engine.evaluate(expr, environment, debugger, 0);
                String result = convertExpressionToResult(evaluated);
                EvalResult evalResult = new EvalResult(result, debugger.getSteps());
                res.end(objectMapper.writeValueAsString(evalResult));
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

    private final List<EvalStep> steps = new ArrayList<>();

    public List<EvalStep> getSteps() {
        return steps;
    }

    @Override
    public void expressionEvaluated(Expression before, Expression after, int depth, long nanoDuration) {
        if (!(before instanceof ListExpression)) {
            return;
        }
        ListExpression expr = (ListExpression) before;
        if (expr.isEmpty()) {
            return;
        }
        Expression first = expr.get(0);
        if (depth > 0 && first.equals(Symbol.of("progn"))) {
            return;
        }
        EvalStep step = new EvalStep();
        step.id = expr.getId();
        step.depth = depth;
        step.form = getStepForm(expr, first);
        step.value = getStepValue(after);
        step.type = after.getValue().getClass().getSimpleName();
        step.us = nanoDuration/1000;
        steps.add(step);
    }

    private String getStepValue(Expression after) {
        String s = after.toString();
        final int threshold = 100;
        if (s.length() <= threshold) {
            return s;
        }
        return s.substring(0, threshold) + "...(omit)";
    }

    private String getStepForm(ListExpression expr, Expression first) {
        if (first.equals(Symbol.of("def")) || first.equals(Symbol.of("apply"))) {
            return first.toString() + " " + expr.get(1);
        }
        return first.toString();
    }

}

class EvalResult {
    public EvalResult(String value, List<EvalStep> steps) {
        this.value = value;
        this.steps = steps;
    }
    public String value;
    public List<EvalStep> steps;
}

class EvalStep {
    public int id;
    public int depth;
    public String form;
    public String value;
    public String type;
    public long us;
}
