package system.allcommonclasses.indexingstructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.utilities.SQLFunctions;

public class SQLStructure extends IndexingStructure{

	/*db and table are currently hard coded, as there is only 1 instance of an
	 * indexing structure at this point -- we could have more and it could be a 
	 * parameter chosen in gui or something
	 */
	private SQLFunctions sqlFunctions;

	public SQLStructure(){
		this.sqlFunctions = new SQLFunctions("test");
		this.sqlFunctions.executeMyQueryNoReturn("CREATE DATABASE IF NOT EXISTS biometrics;");
		this.sqlFunctions.executeMyQueryNoReturn("use biometrics;");
		this.sqlFunctions.executeMyQueryNoReturn("create table if not exists indexing (bi text, userid text);");
	}

	/* There should be a table in biometrics called "indexing"
	 * ________________
	 * |BI         |ID| //BI == "BigInteger"
	 * |1234567890 |45|
	 * |...		   |..|
	 * |___________|__|
	 */
	@Override
	public void add(BigInteger bin, IndexingPoint pointToAdd) {
		this.sqlFunctions.executeMyQueryNoReturn("insert into indexing values("+pointToAdd.getValue().toString()+", "+pointToAdd.getUserID().toString()+");");
	}
	
	@Override
	public void add(ArrayList<IndexingPoint> indexingPoints){
		int arraySize = indexingPoints.size();
		while(arraySize != 0){
			int sizeOfQuery = 0;
			String query = "insert into indexing values ";
			for(IndexingPoint pointToAdd : indexingPoints){
				query += "("+pointToAdd.getValue().toString()+", "+pointToAdd.getUserID().toString()+"),";
				sizeOfQuery += 1;
				if(sizeOfQuery == 1000 || sizeOfQuery == arraySize){
					arraySize -= sizeOfQuery;
					continue;
				}
			}
	        query = query.substring(0, query.length()-1);
			query += ";";
			this.sqlFunctions.executeMyQueryNoReturn(query);
		}
	}

	@Override
	public ArrayList<IndexingPoint> getBinContents(ArrayList<BigInteger> bins){
//		select bi,group_concat(userid) from indexing where bi in (1152747,115277,1152779,1152805) group by bi;		
		ArrayList<IndexingPoint> binContents = new ArrayList<IndexingPoint>();
		try {
			String in = "(";
			for( BigInteger bi : bins){
				in += bi.toString() + ",";
			}
			in = in.substring(0, in.length()-1);
			in += ")";
	//			System.out.println(bin.toString());
//			System.out.println("select bi,group_concat(userid) from indexing where bi in " + in + " group by bi;");
			ResultSet rs1 = this.sqlFunctions.executeMyQuery("select bi,group_concat(userid) from indexing where bi in " + in + " group by bi;"); 
			while(rs1.next()){
				IndexingPoint pointToReturn = new IndexingPoint();
//				System.out.println(rs1.getString(1) + rs1.getString(2));
				String myBigInt = rs1.getString(1);
				if (myBigInt == null){
					pointToReturn.setValue(null);
					pointToReturn.setUserID(null);					
				} else {
					BigInteger value = new BigInteger(myBigInt);
					String userIDs = rs1.getString(2);
					String[] userIDParts = userIDs.split(",");
					for(String userID: userIDParts){
						pointToReturn.setValue(value);
						Long userIDL = new Long(userID);
						pointToReturn.setUserID(userIDL);
					}
				}
				binContents.add(pointToReturn);
//				System.out.println("Just added a point");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return binContents;
	}
	
	@Override
	public ArrayList<IndexingPoint> getBinContents(BigInteger bin) {
		//select bi,group_concat(userid) from indexing group by bi order by bi;
		ArrayList<IndexingPoint> binContents = new ArrayList<IndexingPoint>();
		try{
//			System.out.println(bin.toString());
			ResultSet rs1 = this.sqlFunctions.executeMyQuery("select bi,group_concat(userid) from indexing where bi = " + bin.toString() + ";");
			while(rs1.next()){
				IndexingPoint pointToReturn = new IndexingPoint();
//				System.out.println(rs1.getString(1));
				String myBigInt = rs1.getString(1);
				if (myBigInt == null){
					pointToReturn.setValue(null);
					pointToReturn.setUserID(null);					
				} else {
					BigInteger value = new BigInteger(myBigInt);
	//				pointToReturn.setValue(value);
					String userIDs = rs1.getString(2);
					String[] userIDParts = userIDs.split(",");
					for(String userID: userIDParts){
//						IndexingPoint pointToReturn = new IndexingPoint();
	//					BigInteger value = new BigInteger(rs1.getString(1));
						pointToReturn.setValue(value);
						Long userIDL = new Long(userID);
						pointToReturn.setUserID(userIDL);
					}
	//				System.out.println(rs1.getString(2));
	//				Long userID = new Long(rs1.getString(2));
	//				pointToReturn.setUserID(userID);
				}
//				System.out.println(pointToReturn.toString());
				binContents.add(pointToReturn);
			}
//			System.out.println(binContents);
//			System.exit(0);
//			ResultSet rs = this.sqlFunctions.executeMyQuery("select * from indexing where BI = "+bin.toString()+";");
//			while(rs.next()){
//				IndexingPoint pointToReturn = new IndexingPoint();
//				BigInteger value = new BigInteger(rs.getString(1));
//				pointToReturn.setValue(value);
//				Long userID = new Long(rs.getString(2));
//				pointToReturn.setUserID(userID);
//				binContents.add(pointToReturn);
//			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return binContents;
	}

	@Override
	public void destroy() {
		this.sqlFunctions.executeMyQueryNoReturn("drop table if exists indexing;");
	}

}