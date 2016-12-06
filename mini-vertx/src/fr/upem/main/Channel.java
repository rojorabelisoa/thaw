package fr.upem.main;
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

	//A déplacer
	public List<String> getAllChannel() throws ClassNotFoundException, SQLException{
		List<String> ret = new ArrayList<>();
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select name "+DefaultValue.getDatabasename()+" where type=\"table\" and name like \"ch_%\";";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);   
		while (rset.next()){
			ret.add(rset.getString(0));
        }
		return ret;
	}
	//
	public void createChannel() throws ClassNotFoundException, SQLException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "CREATE TABLE IF NOT EXISTS "+this.name+" (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " message text,\n"
                + "	iduser integer\n"
                + ");";
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** creation channel réussi ***");
        
	}
	public String getAllMessageFromChannel() throws ClassNotFoundException, SQLException{
		//get data
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select message from " + this.getName();
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		return QueryFactoryKit.getFormattedResult(rset).toString();
	}
	public String getName() {
		return name;
	}
	
}
