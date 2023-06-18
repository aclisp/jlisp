package jlisp.runner.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import jlisp.engine.Expression;
import jlisp.parser.Json;
import jlisp.parser.Symbolic;
import jlisp.parser.json.Node;

public class JsonHandler implements Handler<RoutingContext> {

    final ObjectMapper objectMapper;

    public JsonHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(RoutingContext ctx) {
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
                Util.endWithException(res, e);
            }
        });
    }

}
