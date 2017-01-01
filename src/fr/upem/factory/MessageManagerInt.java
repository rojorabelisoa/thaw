package fr.upem.factory;

import java.io.IOException;
import java.sql.SQLException;

import fr.upem.model.Channel;
import fr.upem.model.Message;
/**
 * @author rrabelis
 */
public interface MessageManagerInt {
	/**
	 * This function catch mmessage and return a text according to the received message
	 * @param channel channel which is connect 
	 * @param message message received
	 * @param iduser user who send message
	 * @return a message
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	Message catchMessage(Channel channel, String message, int iduser) throws ClassNotFoundException, SQLException, IOException, InterruptedException;
}
