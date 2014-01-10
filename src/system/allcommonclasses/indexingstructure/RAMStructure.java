package system.allcommonclasses.indexingstructure;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;


public class RAMStructure extends IndexingStructure{

	HashMap<BigInteger, ArrayList<IndexingPoint>> indexingStructure;
	
	public RAMStructure(){
		indexingStructure = new HashMap<BigInteger, ArrayList<IndexingPoint>>();
	}
	
	@Override
	public void add(BigInteger bin, IndexingPoint pointToAdd) {
		ArrayList<IndexingPoint> binContents = indexingStructure.get(bin);
		if(binContents == null){
			binContents = new ArrayList<IndexingPoint>();
			indexingStructure.put(bin, binContents);
		}
		binContents.add(pointToAdd);
		
	}

	@Override
	public ArrayList<IndexingPoint> getBinContents(BigInteger bin) {
		return indexingStructure.get(bin);
	}

}
