package start.application;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class DataBaseApplication extends AbstractVerticle {
    private SQLClient mysqlClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        mysqlClient = MySQLClient.createShared(vertx, new JsonObject()
                .put("host", config().getString("mysql.host", "localhost"))
                .put("port", config().getInteger("mysql.port", 3306))
                .put("username", config().getString("mysql.username", "root"))
                .put("password", config().getString("mysql.password", "123456"))
                .put("database", config().getString("mysql.database", "testDb"))
                .put("maxPoolSize", config().getInteger("mysql.maxPoolSize", 10)));
        mysqlClient.getConnection(ar -> {
            if (ar.failed()) {
                startFuture.fail(ar.cause());
            } else {
                //重点
                SQLConnection connection = ar.result();
                vertx.eventBus().consumer(config().getString("database-service", "database-service"), this::onMessage);
                startFuture.complete();
            }
        });

    }

    private void onMessage(Message<JsonObject> message) {
        if (!message.headers().contains("action")) {
            message.fail(400, "No action header specified");
            return;
        }
        String action = message.headers().get("action");
    }
}
