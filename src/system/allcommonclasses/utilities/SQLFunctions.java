package system.allcommonclasses.utilities;

import java.sql.*;
import java.util.ArrayList;

//very basic sql stuff, quering database
//creating tables, anywhere where we use sql, put it in her
//hide all sql details--don't want to worry about eg details of connection
public class SQLFunctions {

	private Statement stmt;
	private Connection dbCon;
	private ResultSet rs;
	private String dbURL = "jdbc:mysql://localhost:3306;DatabaseName=";//change to '/biometrics'
	private String username ="root";
    private String password = "biometrics";
    
	public SQLFunctions(String databaseName){
        dbCon = null;
        stmt = null;
        rs = null;
	}
	
	public void connectToDatabase() throws ClassNotFoundException{
		try {
			Class.forName("Connector/J");
            dbCon = DriverManager.getConnection(dbURL, username, password);	     
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable(String tableName, Object attributeOne, Object attributeTwo){
		String createTableStatement = "create table "+tableName+"("+attributeOne+' '+' '+attributeTwo+' '+attributeOne.getClass().toString()+";";
		try {
			this.stmt = this.dbCon.prepareStatement(createTableStatement);
			this.rs = this.stmt.executeQuery(createTableStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//add to table
	
	public void dropTable(String tableName){
		String createTableStatement = "drop table "+tableName+";";
		try {
			this.stmt = this.dbCon.prepareStatement(createTableStatement);
			this.rs = this.stmt.executeQuery(createTableStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeMyQuery(String query){
		try {
			this.stmt = this.dbCon.prepareStatement(query);
			this.rs = this.stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	

    public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String db) {
		this.dbURL += db;
	}
}