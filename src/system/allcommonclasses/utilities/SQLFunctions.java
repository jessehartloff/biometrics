package system.allcommonclasses.utilities;

import java.sql.*;
import java.util.ArrayList;

//very basic sql stuff, quering database
//creating tables, anywhere wher we use sql, put it in her
//hide all sql details--don't want to worry about eg details of connectin
public class SQLFunctions {

	public SQLFunctions(){
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=rootpassword");
			System.out.println("Connected database successfully...");
	     
	      //STEP 4: Execute a query
	      System.out.println("Creating table in given database...");
	      stmt = conn.createStatement();
	      
	      String sql = "CREATE TABLE Fingerprints " +
	                   "(id INTEGER PRIMARY KEY, " +
	                   "BigInteger INTEGER PRIMARY KEY)";
	      stmt.executeUpdate(sql);
	      System.out.println("Created table in given database...");
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}
	public void createTable(ArrayList<String> attributeOne, ArrayList<String> attributeTwo){
		
	}
	
	
	//create tables
	// destroy tables when finished

	
}
