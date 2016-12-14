package fr.upem.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import fr.upem.dao.DbConnection;
public class Message {
	
	private int idUser;
	private String message;
	
	public Message(int idUser,String message){
		this.idUser = idUser;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void insertMessage(Channel channel) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into channel_"+channel.getName()+" (message,iduser) values (\""+message+"\" ,"+idUser+")";
		System.out.println(query);
		Statement s=con.createStatement();
        s.executeUpdate(query);  
        System.out.println(" *** insertion message réussi ***");
	}
	
	
	
	

}
