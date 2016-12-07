package fr.upem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.upem.properties.*;
/**
 * @author rrabelis
 */
public class DbConnection
{
    Connection conn;
    public DbConnection(){conn = null;}
    /**
     * this function make connection java and sqlite with jdbc
     * @return Connection succes
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection GetConn() throws ClassNotFoundException, SQLException
    {
    	  Class.forName("org.sqlite.JDBC");  
    	  String onnectionurl = "jdbc:sqlite:"+DefaultValue.getDatabasename();
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