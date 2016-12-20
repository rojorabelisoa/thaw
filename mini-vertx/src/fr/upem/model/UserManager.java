package fr.upem.model;

import java.io.IOException;
import java.sql.SQLException;

public class UserManager {
	
	public User getUser(String name) throws ClassNotFoundException, SQLException, IOException{
		return User.newUser(name);
	}

}
