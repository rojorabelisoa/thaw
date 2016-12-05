package fr.upem.main;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

// java
// --add-exports java.base/sun.nio.ch=ALL-UNNAMED
// --add-exports java.base/sun.net.dns=ALL-UNNAMED
// ExampleApp
public class ExampleApp extends AbstractVerticle {
	// method to run the app

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new ExampleApp());
	}

	@Override
	public void start() throws Exception {
		
		Router router = Router.router(vertx);
		// Allow events for the specified addresses in/out of the event bus
		// bridge
		BridgeOptions opts = new BridgeOptions().addInboundPermitted(new PermittedOptions().setAddress("to-server"))
				.addOutboundPermitted(new PermittedOptions().setAddress("to-client"));
		// route to JSON REST APIs 
	    router.get("/all").handler(arg0 -> {
			try {
				getAllDBs(arg0);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	    
		// the event bus bridge is created and added to the router.
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route("/eventbus/*").handler(ebHandler);
		// A router for the static content.
		router.route().handler(StaticHandler.create());
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		System.out.println("listen on port 8080");
		EventBus eb = vertx.eventBus();

		// Register to listen for messages coming into the server
		eb.consumer("to-server").handler(message -> {
			// Create a timestamp string
			String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
					.format(Date.from(Instant.now()));
			// Send the message back out to all clients with the timestamp
			// prepended.
			eb.publish("to-client", timestamp + ": " + message.body());
		});
	}
	private void getAllDBs(RoutingContext routingContext) throws ClassNotFoundException, SQLException {
	    routingContext.response()
	       .putHeader("content-type", "application/json")
	       .end(Channel.getAllMessage());
	  }
}
