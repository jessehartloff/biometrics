package system.allcommonclasses.indexingstructure;

import java.math.BigInteger;
import java.util.ArrayList;

public abstract class IndexingStructure {
	
	public abstract void add(BigInteger bin, IndexingPoint pointToAdd);
	
	public abstract ArrayList<IndexingPoint> getBinContents(BigInteger bin);

	public abstract void add(ArrayList<IndexingPoint> indexingPoints);
	
	public abstract ArrayList<IndexingPoint> getBinContents(ArrayList<BigInteger> bins);

	public abstract void destroy() ;

}
