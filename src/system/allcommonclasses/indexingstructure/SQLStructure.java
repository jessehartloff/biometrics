package system.allcommonclasses.indexingstructure;
import java.sql.Connection;
import java.sql.SQLException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import system.allcommonclasses.utilities.SQLFunctions;

public class SQLStructure extends IndexingStructure{

	{}// TODO Jen/Jim - SQL indexing
	/*db and table are currently hard coded, as there is only 1 instance of an
	 * indexing structure at this point -- we could have more and it could be a 
	 * parameter chosen in gui or something
	 */
	private static String dbToUse = "fec";//"biometrics";
	private static String tableToUse = "fingerprint";

	private SQLFunctions sqlFunctions;

	//this needs to be some sort of sql table, only used internally
	HashMap<BigInteger, ArrayList<IndexingPoint>> indexingStructure;
	
	public SQLStructure() throws ClassNotFoundException{
		this.sqlFunctions = new SQLFunctions(null);
		this.sqlFunctions.setDbURL(dbToUse);
		this.sqlFunctions.connectToDatabase();
		this.sqlFunctions.createTable("fingerprint", "BigInteger", "IndexingPoint");
		IndexingPoint hey = new IndexingPoint();
		hey.setUserID(23243L);
		hey.setValue(BigInteger.TEN);
		this.add(hey.getValue(), hey);
		//again, we won't use a hashmap here, but a table
//		indexingStructure = new HashMap<BigInteger, ArrayList<IndexingPoint>>();
//		this.sqlFunctions.createTable(this.sqlFunctions.getDb(), "attributeOne", "attributeTwo");
		System.exit(0);
	}

	
	@Override
	public void add(BigInteger bin, IndexingPoint pointToAdd) {
		//  Auto-generated method stub
		//sqlFunctions.addToTable
		this.sqlFunctions.executeMyQuery("insert into blah(\"Venu\",10000);");
	}

	@Override
	public ArrayList<IndexingPoint> getBinContents(BigInteger bin) {
		//  Auto-generated method stub
		//for item in (select * from table where bin = any(select bigInt from table)):
		//set IndexingPoint.value = bin; set userID = ID
		ArrayList<IndexingPoint> binContents = new ArrayList<IndexingPoint>();
//		for(this.sqlFunctions.executeMyQuery("select tableBin,userID from fingerprint where bin = tableBin")
		return null;
	}

	public SQLFunctions getSqlFunctions() {
		return sqlFunctions;
	}
}