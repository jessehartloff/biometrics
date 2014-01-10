package system.allcommonclasses.indexingstructure;

import java.math.BigInteger;
import java.util.ArrayList;

public abstract class IndexingStructure {

	public IndexingStructure(){
		
	}
	
	public abstract void add(BigInteger bin, IndexingPoint pointToAdd);
	public abstract ArrayList<IndexingPoint> getBinContents(BigInteger bin);
	
	// remove point?
	// remember enrollees or number of enrollees?
	
}
