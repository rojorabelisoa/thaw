package fr.upem.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import fr.upem.dao.DbConnection;
public class Message {
	
	private final int idUser;
	private final String message;
	private final Date date;
	
	public Message(int idUser,String message){
		this.idUser = idUser;
		this.message = message;
		this.date = new Date();
	}
	public Date getDate() {
		return date;
	}


	public String getMessage() {
		return message;
	}


	public void insertMessage(Channel channel) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into channel_"+channel.getName()+" (message,iduser,date) values (\""+message+"\" ,"+idUser+",(CURRENT_TIMESTAMP))";
		System.out.println(query);
		Statement s=con.createStatement();
        s.executeUpdate(query);  
        System.out.println(" *** insertion message réussi ***");
	}
	
	
	
	

}
