package jlisp.runner.handler;

import jlisp.engine.Expression;
import jlisp.parser.Symbolic;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class OneLineHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            HttpServerResponse res = ctx.response();
            res.putHeader("content-type", "text/plain");
            try {
                String code = body.toString();
                Expression expr = Symbolic.parse(code);
                res.end(Symbolic.format(expr, false));
            } catch (Exception e) {
                Util.endWithException(res, e);
            }
        });
    }

}
