package fr.upem.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.upem.dao.DbConnection;

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
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select * from News";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);  
		int rows=0;
		while (rset.next()){
			System.out.println(rows+": "+rset.getString("message"));
			rows++;
        }
		System.out.print("number rows:"+rows);
		
    }
}
