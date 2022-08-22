package formular.runner.handler;

import java.io.PrintWriter;
import java.io.StringWriter;

import io.vertx.core.http.HttpServerResponse;

public class Util {
    static void endWithException(HttpServerResponse res, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        res.putHeader("content-type", "text/plain");
        res.setStatusCode(500).end(sw.toString());
    }
}
