package fr.upem.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.upem.bots.SaveManager;
import fr.upem.model.Channel;
import fr.upem.model.Message;

//Ici on écrira juste les properties, puis on dira au serveur qu'il faut créer un nouveau bot avec ces properties.

public class MessageBotManager implements MessageManagerInt{
	private Map<String,fr.upem.bots.Bots> Bots = new HashMap<>();
	private final SaveManager SM = new SaveManager("bots-umlv.txt","/Users/rojo/Documents/git/dossier-sans-titre/mini-vertx/");
	
	public synchronized void createBot(String name, String type, String addr, String name2, String propertiesName) throws FileNotFoundException, IOException{
		fr.upem.bots.Bots b = new fr.upem.bots.Bots(name,type,addr,name2);
		synchronized(Bots){
			Bots.put(name, b);
		}
		SM.save(b, propertiesName);
	}
	
	public synchronized void loadBots() throws FileNotFoundException, IOException{
		List<fr.upem.bots.Bots> tmp = SM.load();
		synchronized(Bots){
			tmp.forEach(Bot -> 
				Bots.put(Bot.getName(), Bot)
					);
		}
	}
	
	public synchronized List<String> getBotResponse(String name) throws InterruptedException, FileNotFoundException, IOException{
		loadBots();
		Thread t = new Thread(Bots.get(name));
		synchronized(Bots){
			t.start();
			t.join();
		}
		return Bots.get(name).getResponse();
		
	}
	@Override
	public Message catchMessage(Channel channel, String message,int iduser) throws IOException, InterruptedException {
		String ret = "";
		String[] token = message.split(" ");
		List<String> list = new ArrayList<>();
		if(token[0].equals("getbot")){
			list = getBotResponse(token[1]);
			for(String t:list){
				ret=ret+""+t+System.getProperty("line.separator");
			}
		}
		else if(token[0].equals("createbot") && token[2].equals("RSS")){
			if(token.length != 5){
				ret = "Bot creation failed, incorrect parameters";
			}
			else{
				createBot(token[1], token[2], token[3], null, token[4]);
				ret="Bot creation succeed";
			}
		}
		else if(token[0].equals("createbot") && token[2].equals("Git")){
			if(token.length != 6){
				ret = "Bot creation failed, incorrect parameters";
			}
			else{
				createBot(token[1], token[2], token[3], token[4], token[5]);
				ret="Bot creation succeed";
			}
		}
		Message m = new Message(iduser,ret);
		return m;
	}


}
