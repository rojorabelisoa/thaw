package fr.upem.factory;

import java.io.IOException;

import fr.upem.model.Channel;

/**
 * @author rrabelis
 */
public class MessageManagerFactory {
	
	/**
	 * The function receives a message and then passes the message into a function which will process the message according to the received message
	 * @param channel
	 * @param message
	 * @param iduser
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static MessageManagerInt getMessageManager(Channel channel, String message, int iduser) throws IOException, InterruptedException{
		String[] token = message.split(" ");
		if(token[0].equals("getbot")||token[0].equals("createbot")){
			MessageBotManager mgt = new MessageBotManager();
			mgt.catchMessage(channel, message,iduser);
		    return mgt;
		}
		else{
			return new MessageManager();
		}
	}
	
}
