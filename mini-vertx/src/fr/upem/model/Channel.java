package fr.upem.model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.upem.dao.DbConnection;
import fr.upem.factory.QueryFactoryKit;
import fr.upem.properties.DefaultValue;

public class Channel {
	private final int id;
	private final String name;
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
		return QueryFactoryKit.getFormattedResult(rset).toString();
	}
	
	public void createChannel() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "CREATE TABLE IF NOT EXISTS channel_"+this.name+" (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " message text,\n"
                + "	iduser integer\n"
                + ");";
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** creation channel réussi ***");
        
	}
	public String getAllMessageFromChannel() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select message from " + this.getName();
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		return QueryFactoryKit.getFormattedResult(rset).toString();
	}
	public String getAllMessageFromChannel(int limit) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select message from " + this.getName();
		if(limit!=0){
			query = query+" limit "+limit;
		}
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		return QueryFactoryKit.getFormattedResult(rset).toString();
	}
	public String getName() {
		return name;
	}
	
}
