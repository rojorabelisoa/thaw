package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DbConnection;
import io.vertx.core.json.JsonObject;
import properties.DefaultValue;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		// Creation database
		/*String url = "jdbc:sqlite:testDB.db";
		 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
		//creation Table
		
		/*DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "CREATE TABLE IF NOT EXISTS News (\n"
                + "	id integer,\n"
                + "	name text,\n"
                + "	message text,\n"
                + "	iduser integer\n"
                + ");";
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** creation channel réussi ***");*/
		
		// insertion données
		/*DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into News (id,name,message,iduser) values (1,\"test\",\"are you there\", 1)";
		Statement s=con.createStatement();
        s.executeUpdate(query);            
        System.out.println(" *** insertion message réussi ***");*/
		
		//get data
		List<String> ret = new ArrayList<>();
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select message from News";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);  
		int rows=0;
		while (rset.next()){
			System.out.println(rows+": "+rset.getString("message"));
			rows++;
        }
		System.out.print("number rows:"+rows);
		
    }
	/*
	 * Convert ResultSet to a common JSON Object array
	 * Result is like: [{"ID":"1","NAME":"Tom","AGE":"24"}, {"ID":"2","NAME":"Bob","AGE":"26"}, ...]
	 */
	public static List<JsonObject> getFormattedResult(ResultSet rset) throws SQLException {
	    List<JsonObject> ret = new ArrayList<JsonObject>();
        // get column names
        ResultSetMetaData rsMD = rset.getMetaData();
        int size = rsMD.getColumnCount();
        List<String> column = new ArrayList<String>();
        for(int i=1;i<=size;i++) {
        	column.add(rsMD.getColumnName(i).toUpperCase());
        }
        while(rset.next()) { // convert each object to an human readable JSON object
        	JsonObject obj = new JsonObject();
            for(int i=1;i<=size;i++) {
                String key = column.get(i - 1);
                String value = rset.getString(i);
                obj.put(key, value);
            }
            ret.add(obj);
        }
	    return ret;
	}
	public static String getAllMessage() throws ClassNotFoundException, SQLException{
		//get data
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select message from News";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);  
		return getFormattedResult(rset).toString();
	}
}
