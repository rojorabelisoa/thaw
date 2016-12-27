package fr.upem.factory;

import java.io.IOException;

import fr.upem.model.Channel;


public class MessageManagerFactory {
	public static MessageManagerInt getMessageManager(Channel channel, String message) throws IOException{
		String[] token = message.split(" ");
		if(token[0].equals("github")|| token[0].equals("rss")){
			MessageBotManager mgt = new MessageBotManager();
			mgt.catchMessage(channel, message);
		    return mgt;
		}
		else{
			return new MessageManager();
		}
	}
}
