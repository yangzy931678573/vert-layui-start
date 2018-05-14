package start.application;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.*;

public class HttpServerApplication extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerApplication.class);
    private static final String HTTP_HOST = "127.0.0.1";
    private static final String REDIS_HOST = "127.0.0.1";
    private static final int HTTP_PORT = 80;
    private static final int REDIS_PORT = 6379;

    @Override
    public void start(Future<Void> future) throws Exception {
        Router router = Router.router(vertx);
        // 跨域支持
       /*
        Set<String> allowHeaders = new HashSet<>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        Set<HttpMethod> allowMethods = new HashSet<>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.PATCH);

        router.route().handler(CorsHandler.create("*") // <2>
                .allowedHeaders(allowHeaders)
                .allowedMethods(allowMethods));
                */
        // 处理http请求体
        router.route()
                .handler(LoggerHandler.create(true, LoggerFormat.TINY))
                .handler(BodyHandler.create())
                .handler(CookieHandler.create())
                .handler(FaviconHandler.create("root/favicon.png"));
        router.route("/").handler(this::index);
        // URI：/static/index.html 定位到/root/index.html
        router.route("/static/*").handler(StaticHandler.create("root"));
        router.route("/page/*").handler(StaticHandler.create("root/pages"));
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(HTTP_PORT, HTTP_HOST, result -> {
                    if (result.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(result.cause());
                    }

                });
    }

    private void index(RoutingContext context) {
        context.reroute(HttpMethod.GET, "/static/index.html");
    }

    private void exceptionHandler(Throwable throwable) {
        throwable.printStackTrace();
    }

}
