package system.allcommonclasses.indexingstructure;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class IndexingStructure {

	
	
	public abstract void add(BigInteger bin, IndexingPoint pointToAdd);
	
	public abstract ArrayList<IndexingPoint> getBinContents(BigInteger bin) throws SQLException;

	//destroy table functionality
	// remove point?
	// remember enrollees or number of enrollees?
	
}
