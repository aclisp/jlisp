package jlisp.runner.handler;

import jlisp.formatter.Formatter;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class FormatHandler implements Handler<RoutingContext> {

    private final static Formatter fmt = new Formatter();

    @Override
    public void handle(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            HttpServerResponse res = ctx.response();
            res.putHeader("content-type", "text/plain");
            try {
                String code = body.toString();
                res.end(fmt.format(code));
            } catch (Exception e) {
                Util.endWithException(res, e);
            }
        });
    }

}
