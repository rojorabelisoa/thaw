package fr.upem.model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import fr.upem.dao.DbConnection;
import fr.upem.util.Utils;
import io.vertx.core.json.JsonObject;

public class Channel {
	@SuppressWarnings("unused")
	private final int id;
	private String name;
	@SuppressWarnings("unused")
	private final int iduser;
	public Channel(int id, String name, int iduser) {
		this.id = id;
		this.name = name;
		this.iduser = iduser;
	}

	public Channel(String name, int iduser) {
		this.id = 1;
		this.name = name;
		this.iduser = iduser;
	}

	public String getAllChannel() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "SELECT name FROM sqlite_master where type=\"table\" and name like \"channel_%\";";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		String ret = Utils.getFormattedResult(rset).toString();
		rset.close();
		con.close();
		return ret;
	}
	public List<JsonObject> getAllChannelName() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "SELECT name FROM sqlite_master where type=\"table\" and name like \"channel_%\";";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		List<JsonObject> ret= Utils.getFormattedResult(rset);
		rset.close();
		con.close();
		return ret;
	}
	
	public void createChannel() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "CREATE TABLE IF NOT EXISTS channel_"+this.name+" (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " message text,\n"
                + "	iduser integer,\n"
                + "	date Datetime\n"
                + ");";
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** creation channels réussi ***");
		con.close();
        
	}
	public String getAllMessageFromChannel() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select message from Channel_" + this.getName();
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		String ret = Utils.getFormattedResult(rset).toString();
		rset.close();
		con.close();
		return ret;
	}
	public String getAllMessageFromChannel(int limit) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select name,message,date from Channel_" + this.getName()+",user where channel_" +this.getName()+".iduser=user.id";
		if(limit!=0){
			query = query+" limit "+limit;
		}
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		String ret = Utils.getFormattedResult(rset).toString();
		rset.close();
		con.close();
		return ret;
	}
	public String getAllMessageFromChannel(String channel,int limit) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select message from Channel_" +channel;
		if(limit!=0){
			query = query+" limit "+limit;
		}
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		String ret = Utils.getFormattedResult(rset).toString();
		rset.close();
		con.close();
		return ret;
	}
	public String getAllMessageFromChannel(String channel) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select name,message,date from Channel_" +channel+",user where channel_" +channel+".iduser=user.id";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		String ret = Utils.getFormattedResult(rset).toString();
		rset.close();
		con.close();
		return ret;
	}
	public String getName() {
		return name;
	}

	public void setChannel(String string) {
		this.name = string;
		
	}
	
}
