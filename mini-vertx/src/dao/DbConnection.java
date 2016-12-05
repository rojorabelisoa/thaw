package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import properties.*;
/**
 * @author rrabelis
 */
public class DbConnection
{
    Connection conn;
    public DbConnection(){conn = null;}

    public Connection GetConn() throws ClassNotFoundException, SQLException
    {
    	  Class.forName("org.sqlite.JDBC");  
    	  String onnectionurl = "jdbc:sqlite:"+DefaultValue.databaseName;
          conn = DriverManager.getConnection(onnectionurl);
          return conn;
    }

    public void commitON() throws SQLException
    {
      conn.setAutoCommit(true);
    }

    public void commitOFF() throws SQLException
    {
      conn.setAutoCommit(false);
    }

    public void close_connection() throws SQLException
    {
      conn.close();
    }

    public void valider() throws SQLException
    {
      conn.commit();
    }

    public void annuler() throws SQLException
    {
      conn.rollback();
    }  
    
}