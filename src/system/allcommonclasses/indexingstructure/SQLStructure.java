package system.allcommonclasses.indexingstructure;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import system.allcommonclasses.utilities.SQLFunctions;

public class SQLStructure extends IndexingStructure{

	{}// TODO Jen/Jim - SQL indexing
	//this needs to be some sort of sql table, only used internally
	HashMap<BigInteger, ArrayList<IndexingPoint>> indexingStructure;
	
	public SQLStructure(){
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

}
