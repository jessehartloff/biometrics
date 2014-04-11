package system.allcommonclasses.indexingstructure;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RAMStructure extends IndexingStructure {

	HashMap<BigInteger, ArrayList<IndexingPoint>> indexingStructure;

	public RAMStructure() {
		indexingStructure = new HashMap<BigInteger, ArrayList<IndexingPoint>>();
	}

	@Override
	public void add(BigInteger bin, IndexingPoint pointToAdd) {
		ArrayList<IndexingPoint> binContents = indexingStructure.get(bin);
		if (binContents == null) {
			binContents = new ArrayList<IndexingPoint>();
			indexingStructure.put(bin, binContents);
		}
		binContents.add(pointToAdd);
	}

	@Override
	public ArrayList<IndexingPoint> getBinContents(BigInteger bin) {
		return indexingStructure.get(bin);
	}

	@Override
	public void add(ArrayList<IndexingPoint> indexingPoints) {
		for(IndexingPoint pointToAdd : indexingPoints){
			this.add(pointToAdd.getValue(),pointToAdd);
		}
	}

	@Override
	public ArrayList<IndexingPoint> getBinContents(ArrayList<BigInteger> bins) {
		ArrayList<IndexingPoint> toReturn = new ArrayList<IndexingPoint>();
		for(BigInteger bin : bins){
			if (this.getBinContents(bin) != null){
				toReturn.addAll(this.getBinContents(bin));
			}
//			for(IndexingPoint pointToReturn: this.getBinContents(bin)){
//				System.out.println("in 2nd for loop");
//				toReturn.add(pointToReturn);
//			}
		}
		return toReturn;
	}

	@Override
	public void destroy() {
		//this function is only used by SQLStructure to get ride of old values in the tables
	}

}
