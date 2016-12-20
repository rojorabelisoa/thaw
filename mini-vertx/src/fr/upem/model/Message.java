package fr.upem.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.upem.dao.DbConnection;
public class Message {
	
	private int idUser;
	private String message;
	private Date date;
	
	public Message(int idUser,String message){
		this.idUser = idUser;
		this.message = message;
		this.date = new Date();
	}
	/*private String getDateNow(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = new Date();
		return sdf.format(d);
	}*/
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void insertMessage(User user) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into channel_"+user.getChannel().getName()+" (message,iduser,date) values (\""+message+"\" ,"+user.getUserId()+",(CURRENT_TIMESTAMP))";
		System.out.println(query);
		Statement s=con.createStatement();
        s.executeUpdate(query);  
        System.out.println(" *** insertion message réussi ***");
	}
	
	
	
	

}
