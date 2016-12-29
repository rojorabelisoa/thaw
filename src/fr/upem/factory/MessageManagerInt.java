package fr.upem.factory;

import java.io.IOException;
import java.sql.SQLException;

import fr.upem.model.Channel;
import fr.upem.model.Message;

public interface MessageManagerInt {
	Message catchMessage(Channel channel, String message, int iduser) throws ClassNotFoundException, SQLException, IOException;
}
