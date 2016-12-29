package fr.upem.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.upem.model.Channel;
import fr.upem.model.Message;

//Ici on écrira juste les properties, puis on dira au serveur qu'il faut créer un nouveau bot avec ces properties.

public class MessageBotManager implements MessageManagerInt{

	@Override
	public Message catchMessage(Channel channel, String message,int iduser) throws IOException {
		String ret = "";
		String[] token = message.split(" ");
		List<String> commitList = new ArrayList<>();
		if(token[0].equals("github")){
			
			commitList = CommitGitFactoryKit.getCommit(token[2], token[1]);
			for(String t:commitList){
				ret=ret+""+t+System.getProperty("line.separator");
			}
		}
		else if(token[0].equals("rss")){
			RSSReader reader = new RSSReader();
			try {
				commitList = reader.Read(token[1]);
				for(String t:commitList){
					ret=ret+""+t+System.getProperty("line.separator");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Message m = new Message(iduser,ret);
		return m;
	}


}
