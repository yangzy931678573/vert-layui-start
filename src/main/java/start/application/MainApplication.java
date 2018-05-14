package start.application;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.impl.launcher.commands.StopCommand;

public class MainApplication extends AbstractVerticle {

    @Override
    public void start(Future<Void> future) throws Exception {
        Future<String> promise = Future.future();
        vertx.deployVerticle(HttpServerApplication.class,
                new DeploymentOptions()
                        .setInstances(1), promise.completer());
        promise.compose(id -> Future.future()).setHandler(ar -> {
            if (ar.succeeded()) {
                future.complete();
            } else {
                future.fail(ar.cause());
            }
        });
    }

}
