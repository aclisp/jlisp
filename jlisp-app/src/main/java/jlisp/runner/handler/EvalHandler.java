package jlisp.runner.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jlisp.engine.Default;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.SimpleDebugger;
import jlisp.engine.SimpleDebugger.EvaluationStep;
import jlisp.parser.Json;
import jlisp.parser.Symbolic;
import jlisp.parser.json.Node;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class EvalHandler implements Handler<RoutingContext> {

    final ObjectMapper objectMapper;

    public EvalHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            HttpServerResponse res = ctx.response();
            res.putHeader("content-type", "application/json");
            try {
                Expression expr = convertBodyToExpression(ctx, body);
                Environment env = Default.environment();
                SimpleDebugger debugger = new SimpleDebugger();
                Expression evaluated;
                try {
                    evaluated = Engine.evaluate(expr, env, debugger);
                } catch (Exception e) {
                    String result = convertEvaluateExceptionToResult(e);
                    EvalResult evalResult = new EvalResult(result, debugger.getSteps());
                    res.end(objectMapper.writeValueAsString(evalResult));
                    return;
                }
                String result = convertExpressionToResult(evaluated);
                EvalResult evalResult = new EvalResult(result, debugger.getSteps());
                res.end(objectMapper.writeValueAsString(evalResult));
            } catch (Exception e) {
                Util.endWithException(res, e);
            }
        });
    }

    private String convertEvaluateExceptionToResult(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
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

class EvalResult {
    public EvalResult(String value, List<EvaluationStep> steps) {
        this.value = value;
        this.steps = steps;
    }
    public String value;
    public List<EvaluationStep> steps;
}
