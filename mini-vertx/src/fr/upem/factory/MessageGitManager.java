package fr.upem.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.upem.model.Channel;
import fr.upem.model.Message;

public class MessageGitManager implements MessageManagerInt{

	@Override
	public Message catchMessage(Channel channel, String message) throws IOException {
		String ret = "";
		String[] token = message.split(" ");
		if(token[0].equals("github")){
			List<String> commitList = new ArrayList<>();
			commitList = CommitGitFactoryKit.getCommit(token[2], token[1]);
			for(String t:commitList){
				ret=ret+""+t+"\n";
			}
		}
		Message m = new Message(1,ret);
		return m;
	}

}
