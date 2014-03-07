package system.allcommonclasses.indexingstructure;
import java.sql.Connection;
import java.sql.SQLException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import system.allcommonclasses.utilities.SQLFunctions;

public class SQLStructure1 extends IndexingStructure{

	{}// TODO Jen/Jim - SQL indexing

	private static String userName = "biometrics";
	private static String password = "skewlIsCool";
	private static Connection connection = null;


	//this needs to be some sort of sql table, only used internally
	HashMap<BigInteger, ArrayList<IndexingPoint>> indexingStructure;
	
	public SQLStructure1(){
		//again, we won't use a hashmap here, but a table
		indexingStructure = new HashMap<BigInteger, ArrayList<IndexingPoint>>();
		SQLFunctions sqlf = new SQLFunctions();
		System.exit(0);
	}

	
	@Override
	public void add(BigInteger bin, IndexingPoint pointToAdd) {
		//  Auto-generated method stub
		
	}

	@Override
	public ArrayList<IndexingPoint> getBinContents(BigInteger bin) {
		//  Auto-generated method stub
		//for item in (select * from table where bin = any(select bigInt from table)):
		//set IndexingPoint.value = bin; set userID = ID
		return null;
	}
	
	public static Connection ConnectToDatabase(){
		try{
			OracleDataSource ds = new OracleDataSource();
	        ds.setUser(userName);
	        ds.setPassword(password);
	        ds.setURL("jdbc:oracle:thin:@aos.acsu.buffalo.edu:1521/aos.buffalo.edu");
	        connection = ds.getConnection();
			
		}catch(SQLException e){
			System.out.println("Cannot connect to database");
			e.printStackTrace();
	        System.exit(1);
		}
		return null;
		
	}

}
