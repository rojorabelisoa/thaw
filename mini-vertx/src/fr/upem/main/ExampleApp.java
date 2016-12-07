package fr.upem.main;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.upem.factory.CommitGitFactoryKit;
import fr.upem.model.Channel;
import fr.upem.model.Message;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
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
    Channel c= new Channel("lemonde",1);
    
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new ExampleApp());
	}

	@Override
	public void start() throws Exception {
		createWebSocket();
		EventBus eb = vertx.eventBus();
		// Register to listen for messages coming into the server
		eb.consumer("to-server").handler(message -> {
			// Create a timestamp string
			String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
					.format(Date.from(Instant.now()));
			// Send the message back out to all clients with the timestamp
			// prepended.
			String text = message.body().toString();
			System.out.println("message body : "+message.body().toString());
			String[] token = text.split(" ");
			String ret = "";
			if(token[0].equals("github")){
				List<String> commitList = new ArrayList<>();
				try {
					commitList = CommitGitFactoryKit.getCommit(token[2], token[1]);
					for(String t:commitList){
						ret=ret+""+t+"\n";
					}
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				Message m = new Message(1,message.body().toString());
				try {
					m.insertMessage(c);
					ret =timestamp + ": " + message.body();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
			eb.publish("to-client", ret);
		});
	}

	private void createWebSocket() {
		Router router = Router.router(vertx);
		// Allow events for the specified addresses in/out of the event bus
		// bridge
		BridgeOptions opts = new BridgeOptions().addInboundPermitted(new PermittedOptions().setAddress("to-server"))
				.addOutboundPermitted(new PermittedOptions().setAddress("to-client"));
		// route to JSON REST APIs 
	    router.get("/all").handler(this::getAllDBs);
	    router.get("/getMessage/:id").handler(this::getARecord);
	    
		// the event bus bridge is created and added to the router.
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route("/eventbus/*").handler(ebHandler);
		// A router for the static content.
		router.route().handler(StaticHandler.create());
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		System.out.println("listen on port 8080");
	}
	private void getAllDBs(RoutingContext routingContext){
		HttpServerResponse response = routingContext.response();
		try {
			routingContext.response()
			   .putHeader("content-type", "application/json")
			   .end(c.getAllMessageFromChannel(0));
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatusCode(404).end();
		      return;
		}
	}
	private void getARecord(RoutingContext routingContext) {
	    HttpServerResponse response = routingContext.response();
	    HttpServerRequest request = routingContext.request();
	    int id = Integer.parseInt(request.getParam("id"));
	    if (id < 0) {  
	      response.setStatusCode(404).end();
	      return;
	    } 
	    try {
			routingContext.response()
			   .putHeader("content-type", "application/json")
			   .end(c.getAllMessageFromChannel(id));
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatusCode(404).end();
		      return;
		} 
	}
}
