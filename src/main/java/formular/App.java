package formular;

import io.vertx.core.Vertx;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        Vertx vertx = Vertx.vertx();
        HttpServerHost server = new HttpServerHost();
        vertx.deployVerticle(server);
    }
}
