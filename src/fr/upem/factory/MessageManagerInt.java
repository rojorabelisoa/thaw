package fr.upem.factory;

import java.io.IOException;
import java.sql.SQLException;

import fr.upem.model.Channel;
import fr.upem.model.Message;

public interface MessageManagerInt {
	Message catchMessage(Channel channel, String message) throws ClassNotFoundException, SQLException, IOException;
}
