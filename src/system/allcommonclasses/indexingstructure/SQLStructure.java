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

	public SQLStructure() throws ClassNotFoundException{
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
	public ArrayList<IndexingPoint> getBinContents(BigInteger bin) throws SQLException {
		ArrayList<IndexingPoint> binContents = new ArrayList<IndexingPoint>();
		try{
			ResultSet rs = this.sqlFunctions.executeMyQuery("select * from indexing where BI = "+bin.toString()+";");
			while(rs.next()){
				IndexingPoint pointToReturn = new IndexingPoint();
				BigInteger value = new BigInteger(rs.getString(1));
				pointToReturn.setValue(value);
				Long userID = new Long(rs.getString(2));
				pointToReturn.setUserID(userID);
				binContents.add(pointToReturn);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return binContents;
	}

}