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
    Channel c= new Channel("Football",1);
    
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new ExampleApp());
	}

	@Override
	public void start() throws Exception {
		createWebSocket();
		EventBus eb = vertx.eventBus();
		eb.consumer("to-server").handler(message -> {
			String ret = "";
			try {
				ret = sendMessage(message);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
			eb.publish("to-client", ret);
		});
	}

	private String sendMessage(io.vertx.core.eventbus.Message<Object> message) throws IOException, ClassNotFoundException, SQLException {
		String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));
		String text = message.body().toString();
		String[] token = text.split(" ");
		String ret = "";
		if(token[0].equals("github")){
			List<String> commitList = new ArrayList<>();
			commitList = CommitGitFactoryKit.getCommit(token[2], token[1]);
			for(String t:commitList){
				ret=ret+""+t+"\n";
			}
		}
		else{
			Message m = new Message(1,message.body().toString());
			m.insertMessage(c);
			ret =timestamp + ": " + message.body();
		}
		return ret;
	}

	private void createWebSocket() {
		Router router = Router.router(vertx);
		BridgeOptions opts = new BridgeOptions().addInboundPermitted(new PermittedOptions().setAddress("to-server"))
				.addOutboundPermitted(new PermittedOptions().setAddress("to-client"));
	    router.get("/all").handler(this::getAllMessages);
	    router.get("/getMessage/:channel").handler(this::getAllMessagesByChannel);
	    router.get("/allChannel").handler(this::getAllChannel);
	    router.get("/getMessage/:id").handler(this::getMessage);
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route("/eventbus/*").handler(ebHandler);
		router.route().handler(StaticHandler.create());
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		System.out.println("listen on port 8080");
	}
	
	private void getAllMessagesByChannel(RoutingContext routingContext){
		HttpServerRequest request = routingContext.request();
	    String channel = request.getParam("channel");
		HttpServerResponse response = routingContext.response();
		try {
			routingContext.response()
			   .putHeader("content-type", "application/json")
			   .end(c.getAllMessageFromChannel(0));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			response.setStatusCode(404).end();
		      return;
		}
	}
	
	private void getAllMessages(RoutingContext routingContext){
		HttpServerResponse response = routingContext.response();
		try {
			routingContext.response()
			   .putHeader("content-type", "application/json")
			   .end(c.getAllMessageFromChannel(0));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			response.setStatusCode(404).end();
		      return;
		}
	}
	
	private void getAllChannel(RoutingContext routingContext){
		HttpServerResponse response = routingContext.response();
		try {
			routingContext.response()
			   .putHeader("content-type", "application/json")
			   .end(c.getAllChannel());
		} catch (ClassNotFoundException | SQLException | IOException e) {
			response.setStatusCode(404).end();
		      return;
		}
	}
	
	private void getMessage(RoutingContext routingContext) {
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
		} catch (ClassNotFoundException | SQLException | IOException e) {
			response.setStatusCode(204).end();
		      return;
		} 
	}
}
