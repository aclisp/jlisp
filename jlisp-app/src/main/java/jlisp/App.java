package jlisp;

import jlisp.runner.HttpServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        Vertx vertx = Vertx.vertx(new VertxOptions()
            .setWorkerPoolSize(1)
            .setEventLoopPoolSize(1)
            .setInternalBlockingPoolSize(2));
        HttpServerVerticle server = new HttpServerVerticle();
        vertx.deployVerticle(server);
    }
}
