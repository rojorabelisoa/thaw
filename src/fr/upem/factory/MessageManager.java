package fr.upem.factory;

import java.io.IOException;
import java.sql.SQLException;

import fr.upem.model.Channel;
import fr.upem.model.Message;
/**
 * @author rrabelis
 */
public class MessageManager implements MessageManagerInt {

	@Override
	public Message catchMessage(Channel channel, String message, int iduser) throws ClassNotFoundException, SQLException, IOException {
		Message m = new Message(iduser,message);
		m.insertMessage(channel);
		return m;
		
	}
}
