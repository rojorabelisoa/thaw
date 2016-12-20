package fr.upem.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.upem.dao.DbConnection;
import fr.upem.pass.passSHA256;

/*
 * only to check if User is already registered and / or register him
 */

public class UserDB {
	
	/*
	 * return true if this user already exists.
	 * else false
	 */
	
	private UserDB(){
		
	}
	
	public static boolean userExists(String name,String password) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		Statement stmt = con.createStatement();
		String query = "SELECT * FROM User WHERE name = \"" + name + "\" and password = \"" + password + "\";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		return rs.isBeforeFirst();
	}
	
	/*
	 * insert a new user into the database
	 */
	
	public static void insertUser(String name,String password) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into User (name,password) values (\"" + name + "\",\"" + password + "\");";
		System.out.println(query);
		Statement stmt=con.createStatement();
        stmt.executeUpdate(query);
        System.out.println(" *** inscription réussi ***");
	}
}
