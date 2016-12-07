package fr.upem.service;

import java.io.IOException;
import java.sql.SQLException;

import fr.upem.model.Channel;

public class ChannelService {
	public static String getAllChannel(Channel c) throws ClassNotFoundException, SQLException, IOException{
		return c.getAllChannel();
	}
	public static void createNewChannel(Channel c){
		
	}
}
