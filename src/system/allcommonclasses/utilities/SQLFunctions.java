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
	private String dbURL = "jdbc:mysql://localhost:3306/";//change '/fec' to '/biometrics'
	private String username ="root";
    private String password = "biometrics";
    
	public SQLFunctions(){
        dbCon = null;
        stmt = null;
        rs = null;
	}
	
	public void connectToDatabase(){
		try {
            dbCon = DriverManager.getConnection(dbURL, username, password);	     
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable(String tableName, String attributeOne, String attributeTwo){
		String createTableStatement = "create table "+tableName+"("+attributeOne+" string ,"+attributeTwo+" string);";
		try {
			this.stmt = this.dbCon.prepareStatement(createTableStatement);
			this.rs = this.stmt.executeQuery(createTableStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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