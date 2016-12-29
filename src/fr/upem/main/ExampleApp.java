package fr.upem.main;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import com.fasterxml.jackson.databind.ObjectMapper;

import fr.upem.factory.MessageManagerFactory;
import fr.upem.factory.MessageManagerInt;
import fr.upem.model.Channel;
import fr.upem.model.Message;
import fr.upem.model.User;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
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
	private Channel c = new Channel("Euronews",1);
	private String userconnect ="";
	private int iduserconnect=1;
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions(); 
		options.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle(new ExampleApp());
	}

	@Override
	public void start() throws Exception {
		createWebSocket();
		EventBus eb = vertx.eventBus();
		eb.consumer("to-server").handler(message -> {
			String ret = "";
			try {
				ret = sendMessage(message,iduserconnect);
				System.out.println(ret);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
			eb.publish("to-client", ret);
		});
	}

	private String sendMessage(io.vertx.core.eventbus.Message<Object> message,int iduser) throws IOException, ClassNotFoundException, SQLException {
		String text = message.body().toString();
		MessageManagerInt mgt = MessageManagerFactory.getMessageManager(c, text,iduser);
		Message messages = mgt.catchMessage(c, text, iduser);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(messages);
		return mapper.writeValueAsString(messages);
	}

	private void createWebSocket() {
		Router router = Router.router(vertx);
		BridgeOptions opts = new BridgeOptions().addInboundPermitted(new PermittedOptions().setAddress("to-server"))
				.addOutboundPermitted(new PermittedOptions().setAddress("to-client"));
	    addroute(router);
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route("/eventbus/*").handler(ebHandler);
		router.route().handler(StaticHandler.create());
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		System.out.println("listen on port 8080");
	}

	private void addroute(Router router) {
		router.get("/all").handler(this::getAllMessages);
		router.get("/login").method(HttpMethod.POST).handler(this::login);
		router.get("/register").method(HttpMethod.POST).handler(this::register);
	    router.get("/createChannel/:channel").handler(this::createChannel);
	    router.get("/current-channel/").handler(this::currentChannel);
	    router.get("/getMessages/:channel").handler(this::getAllMessagesByChannel);
	    router.get("/getMessages/:channel/:limit").handler(this::getAllMessagesByChannelCount);
	    router.get("/allChannel").handler(this::getAllChannel);
	    router.get("/getMessage/:id").handler(this::getMessage);
	    router.route("/logout").handler(this::logout);
	}

	private void logout(RoutingContext routingContext) {
		routingContext.clearUser();
		//Session session = routingContext.session();
		//session.destroy();
		routingContext.response().putHeader("location", "/").setStatusCode(302).end();
	}
	private void currentChannel(RoutingContext routingContext) {
		//Session session = routingContext.session();
		//String user = session.get("username");
		String user = userconnect;
		String ret = "[{\"current\":\""+c.getName()+"\",\"user\":\""+user+"\"}]";
		routingContext.response()
        .putHeader("Cache-Control", "no-store, no-cache")
        .putHeader("X-Content-Type-Options", "nosniff")
        .putHeader("Strict-Transport-Security", "max-age=" + 15768000)
        .putHeader("X-Download-Options", "noopen")
        .putHeader("X-XSS-Protection", "1; mode=block")
        .putHeader("X-FRAME-OPTIONS", "DENY")
		.putHeader("content-type", "application/json")
		.end(ret);
	}
	
	private void login(RoutingContext routingContext){
		HttpServerRequest request = routingContext.request();
		request.bodyHandler(new Handler<Buffer>()
        {
            @Override
            public void handle(Buffer buff)
            {
            	//Session session = routingContext.session();
                String contentType = request.headers().get("Content-Type");
                if ("application/x-www-form-urlencoded".equals(contentType))
                {
                    QueryStringDecoder qsd = new QueryStringDecoder(buff.toString(), false);
                    Map<String, List<String>> params = qsd.parameters();
                    String user = params.get("username").get(0);
                    String password = params.get("password").get(0);
                    try {
                    	List<JsonObject> users = User.testUserValide(user,password);
						if(users != null){
							for(JsonObject jo : users){
								iduserconnect = Integer.parseInt((String)jo.getValue("ID"));
								userconnect = (String) jo.getValue("NAME");
								
							}
							//session.put("username", "huhu");
							//session.put("iduser", id);
						    routingContext.response().putHeader("location", "/chat.html").setStatusCode(302).end();
						}
						else{
							routingContext.response().putHeader("location", "/").setStatusCode(302).end();
						}
					} catch (ClassNotFoundException | SQLException | IOException e) {
						e.printStackTrace();
					}
                    
                }
            }
        });
	}
	private void register(RoutingContext routingContext){
		HttpServerRequest request = routingContext.request();
		request.bodyHandler(new Handler<Buffer>()
        {
            @Override
            public void handle(Buffer buff)
            {
                String contentType = request.headers().get("Content-Type");
                if ("application/x-www-form-urlencoded".equals(contentType))
                {
                    QueryStringDecoder qsd = new QueryStringDecoder(buff.toString(), false);
                    Map<String, List<String>> params = qsd.parameters();
                    String user = params.get("username").get(0);
                    String password = params.get("password").get(0);
                    User u= new User(user, password);
                    try {
                    	u.createUser();
					    routingContext.response().putHeader("location", "/").setStatusCode(302).end();
						
					} catch (ClassNotFoundException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                }
            }
        });
	}

	private void getAllMessagesByChannel(RoutingContext routingContext){
		HttpServerRequest request = routingContext.request();
		String channel = request.getParam("channel");
	    Objects.requireNonNull(channel);
	    c.setChannel(channel);
		HttpServerResponse response = routingContext.response();
		try {
			routingContext.response()
	          .putHeader("Cache-Control", "no-store, no-cache")
	          .putHeader("X-Content-Type-Options", "nosniff")
	          .putHeader("Strict-Transport-Security", "max-age=" + 15768000)
	          .putHeader("X-Download-Options", "noopen")
	          .putHeader("X-XSS-Protection", "1; mode=block")
	          .putHeader("X-FRAME-OPTIONS", "DENY")
			  .putHeader("content-type", "application/json")
			  .end(c.getAllMessageFromChannel(channel));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			response.setStatusCode(404).end();
		      return;
		}
	}
	private void createChannel(RoutingContext routingContext){
		HttpServerRequest request = routingContext.request();
	    String channel = request.getParam("channel");
	    Objects.requireNonNull(channel);
	    Channel newchannel = new Channel(channel, iduserconnect) ;
		try {
			 newchannel.createChannel();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			
		      return;
		}
	}
	
	private void getAllMessagesByChannelCount(RoutingContext routingContext){
		HttpServerRequest request = routingContext.request();
	    String channel = request.getParam("channel");
	    Objects.requireNonNull(channel);
	    int limit = Integer.parseInt(request.getParam("limit"));
		HttpServerResponse response = routingContext.response();
		try {
			routingContext.response()
	          .putHeader("Cache-Control", "no-store, no-cache")
	          .putHeader("X-Content-Type-Options", "nosniff")
	          .putHeader("Strict-Transport-Security", "max-age=" + 15768000)
	          .putHeader("X-Download-Options", "noopen")
	          .putHeader("X-XSS-Protection", "1; mode=block")
	          .putHeader("X-FRAME-OPTIONS", "DENY")
			  .putHeader("content-type", "application/json")
			  .end(c.getAllMessageFromChannel(channel,limit));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			response.setStatusCode(404).end();
		      return;
		}
	}
	
	private void getAllMessages(RoutingContext routingContext){
		HttpServerResponse response = routingContext.response();
		try {
			routingContext.response()
			  .putHeader("Cache-Control", "no-store, no-cache")
	          .putHeader("X-Content-Type-Options", "nosniff")
	          .putHeader("Strict-Transport-Security", "max-age=" + 15768000)
	          .putHeader("X-Download-Options", "noopen")
	          .putHeader("X-XSS-Protection", "1; mode=block")
	          .putHeader("X-FRAME-OPTIONS", "DENY")
			  .putHeader("content-type", "application/json")
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
			  .putHeader("Cache-Control", "no-store, no-cache")
	          .putHeader("X-Content-Type-Options", "nosniff")
	          .putHeader("Strict-Transport-Security", "max-age=" + 15768000)
	          .putHeader("X-Download-Options", "noopen")
	          .putHeader("X-XSS-Protection", "1; mode=block")
	          .putHeader("X-FRAME-OPTIONS", "DENY")
			  .putHeader("content-type", "application/json")
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
			  .putHeader("Cache-Control", "no-store, no-cache")
	          .putHeader("X-Content-Type-Options", "nosniff")
	          .putHeader("Strict-Transport-Security", "max-age=" + 15768000)
	          .putHeader("X-Download-Options", "noopen")
	          .putHeader("X-XSS-Protection", "1; mode=block")
	          .putHeader("X-FRAME-OPTIONS", "DENY")
			  .putHeader("content-type", "application/json")
			  .putHeader("content-type", "application/json")
			  .end(c.getAllMessageFromChannel(id));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			response.setStatusCode(204).end();
		      return;
		} 
	}
}
