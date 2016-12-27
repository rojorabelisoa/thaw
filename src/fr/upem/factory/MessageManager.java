package fr.upem.factory;

import java.io.IOException;
import java.sql.SQLException;

import fr.upem.model.Channel;
import fr.upem.model.Message;

public class MessageManager implements MessageManagerInt {

	@Override
	public Message catchMessage(Channel channel, String message) throws ClassNotFoundException, SQLException, IOException {
		Message m = new Message(1,message);
		m.insertMessage(channel);
		return m;
		
	}
}
