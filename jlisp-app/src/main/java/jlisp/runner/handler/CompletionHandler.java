package jlisp.runner.handler;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jlisp.engine.Default;
import jlisp.engine.Environment;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class CompletionHandler implements Handler<RoutingContext> {

    final ObjectMapper objectMapper;
    final Environment environment;

    public CompletionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.environment = Default.environment();;
    }

    @Override
    public void handle(RoutingContext ctx) {
        String prefix = ctx.request().getParam("prefix", "");
            List<String> completions = environment.complete(prefix);
            HttpServerResponse res = ctx.response();
            try {
                String json = objectMapper.writeValueAsString(completions);
                res.putHeader("content-type", "application/json");
                res.end(json);
            } catch (JsonProcessingException e) {
                Util.endWithException(res, e);
            }
    }

}
