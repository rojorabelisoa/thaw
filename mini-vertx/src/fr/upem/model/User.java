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

public class User {
	private final int id;
	private final String name;
	private final String password;
	public User(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public User(String name, String password) {
		this.id = 1;
		this.name = name;
		this.password = password;
	}
	public void createUser() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into user (name,password) values (\""+this.name+"\" ,\""+this.password+"\")";
		System.out.println(query);
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** creation user réussi ***");
	}
	public void updateUser() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "update user set name =\""+this.name+"\" ,password = \""+this.password+"\" where id="+this.id+"";
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** modification user réussi ***");
	}
	public void deleteUser() throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "delete from user id="+this.id+"";
		Statement s=con.createStatement();
		s.execute(query);            
        System.out.println(" *** modification user réussi ***");
	}
	public List<JsonObject> selectUser(String condition) throws ClassNotFoundException, SQLException, IOException{
		DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "SELECT * FROM user where 1<2";
		if(!condition.equals("")){
			query+=" and "+condition;
		}
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);
		return Utils.getFormattedResult(rset);
	}
}
