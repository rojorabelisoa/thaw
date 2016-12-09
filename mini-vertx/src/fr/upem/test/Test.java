package fr.upem.test;

import fr.upem.model.Channel;

public class Test {
	public static void main(String[] args) throws Exception{
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
		
		Channel news=new  Channel("Basket",2);
		news.createChannel();
		
		// insertion données
		/*DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "insert into News (id,name,message,iduser) values (1,\"test\",\"are you there\", 1)";
		Statement s=con.createStatement();
        s.executeUpdate(query);            
        System.out.println(" *** insertion message réussi ***");*/
		
		//get data
		/*DbConnection db=new DbConnection();
		Connection con =db.GetConn();
		String query = "select * from News";
		Statement st=con.createStatement();
		ResultSet rset=st.executeQuery(query);  
		int rows=0;
		while (rset.next()){
			System.out.println(rows+": "+rset.getString("message"));
			rows++;
        }
		System.out.print("number rows:"+rows);*/
		/*List<String> commitList = new ArrayList<>();
		commitList = CommitGitFactoryKit.getCommit("thaw", "rojorabelisoa");
		for(String t:commitList){
			System.out.println(t);
		}*/
		
    }
}
