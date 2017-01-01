package fr.upem.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author rrabelis
 */
public class DbConnection
{
    private Connection conn;
    public DbConnection(){conn = null;}
    /**
     * this function make connection java and sqlite with jdbc
     * @return Connection succes
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    public Connection GetConn() throws ClassNotFoundException, SQLException, IOException
    {
    	  Class.forName("org.sqlite.JDBC");  
    	  String onnectionurl = "jdbc:sqlite:testDB.db";
          conn = DriverManager.getConnection(onnectionurl);
          return conn;
    }
    
}