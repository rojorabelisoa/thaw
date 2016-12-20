package fr.upem.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.upem.dao.DbConnection;

public class User {
	
	private Channel channel;
	private final String name;
	private final int userId;
	
	private User(String name) throws ClassNotFoundException, SQLException, IOException{
		this.name = name;
		this.userId = getUserIdFromDB(name);
	}
	
	public void changeChannel(Channel channel){
		this.channel = channel;
	}
	
	private int getUserIdFromDB(String name) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		Statement stmt = con.createStatement();
		String query = "SELECT * FROM User WHERE name = \"" + name + "\";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		return rs.getInt(1);
	}
	
	public static User newUser(String name) throws ClassNotFoundException, SQLException, IOException{
		return new User(name);
	}

}
