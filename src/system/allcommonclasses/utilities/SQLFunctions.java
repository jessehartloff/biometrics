package system.allcommonclasses.utilities;

import java.sql.*;
import java.util.ArrayList;

public class SQLFunctions {

	private Statement stmt;
	private Connection dbCon;
	private ResultSet rs;
	private int rsNR;//"result set no return
	private String dbURL = "jdbc:mysql://localhost:3306/";
	private String username ="root";
    private String password = "biometrics";
    
	public SQLFunctions(String database){
		this.dbURL += database;	
        dbCon = null;
		try {
            dbCon = DriverManager.getConnection(dbURL, username, password);	     
		} catch (SQLException e) {
			e.printStackTrace();
		}
        stmt = null;
        rs = null;
	}
	
	//Use this whenever we want to return something
	public ResultSet executeMyQuery(String query){
		try {
			this.stmt = this.dbCon.prepareStatement(query);
			this.rs = this.stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/*Use this for statements like "insert", "delete", "update"
	 */
	public void executeMyQueryNoReturn(String query){
		try {
			this.stmt = this.dbCon.prepareStatement(query);
			this.rsNR = this.stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}