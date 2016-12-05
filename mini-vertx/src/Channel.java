import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DbConnection;
import properties.DefaultValue;

public class Channel {
	private final int id;
	private final String name;
	private final String message;
	private final int iduser;
	public Channel(int id, String name, String message, int iduser) {
		this.id = id;
		this.name = name;
		this.message = message;
		this.iduser = iduser;
	}
	public List<String> getAllChannel() throws ClassNotFoundException, SQLException{
		List<String> ret = new ArrayList<>();
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select name "+DefaultValue.databaseName+" where type=\"table\" and name like \"ch_%\";";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);   
		while (rset.next()){
			ret.add(rset.getString(0));
        }
		return ret;
	}
	public void createChannelMessage(Channel channel) throws ClassNotFoundException, SQLException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "CREATE TABLE IF NOT EXISTS "+channel.name+" (\n"
                + "	id integer,\n"
                + "	name text,\n"
                + "	message text,\n"
                + "	iduser integer\n"
                + ");";
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** creation channel réussi ***");
        
	}
	public void insertMessage(Channel channel) throws ClassNotFoundException, SQLException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into "+channel.name+" (name,message,iduser) values ("+channel.name+", "+channel.message+", 1";
		Statement s=con.createStatement();
        s.executeUpdate(query);            
        System.out.println(" *** insertion message réussi ***");
	}
	
}
